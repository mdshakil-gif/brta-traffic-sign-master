package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.MyApplicationTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

// --- Data Models ---

data class AppSettings(
    val quizLength: Int = 10,
    val appNotice: String = "",
    val appPurpose: String = "উদ্দেশ্য: সম্মানিত চালক ভাইদের ট্রাফিক সাইন পরিচিতি ও লাইসেন্স পরীক্ষা সহজ করে ড্রাইভিং দক্ষতা বৃদ্ধির লক্ষ্যে এই অ্যাপটি তৈরি করেছেন:",
    val developerName: String = "Hafez Shakil Ahmed",
    val developerPhone: String = "",
    val developerEmail: String = "",
    val facebookLink: String = "https://www.facebook.com/share/1EEjYNnJCD/"
)

data class NotificationItem(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val link: String? = null,
    val timestamp: Long = 0,
    val read: Boolean = false,
    val readCount: Long = 0,
    val linkClicks: Long = 0
)

enum class AppScreen {
    HOME, CATALOG, EXAM, NOTIFICATIONS, WINNER
}

const val APP_LOGO_SVG = """<svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
  <circle cx="50" cy="50" r="48" fill="#006A4E" />
  <circle cx="50" cy="50" r="38" fill="#F42A41" />
  <path d="M 45 20 L 55 20 L 70 80 L 30 80 Z" fill="#333333" />
  <rect x="49" y="23" width="2" height="6" fill="#FFFFFF" />
  <rect x="48.7" y="32" width="2.6" height="7" fill="#FFFFFF" />
  <rect x="48.4" y="42" width="3.2" height="8" fill="#FFFFFF" />
  <rect x="48" y="53" width="4" height="9" fill="#FFFFFF" />
  <rect x="47.6" y="65" width="4.8" height="10" fill="#FFFFFF" />
  <rect x="47.2" y="78" width="5.6" height="8" fill="#FFFFFF" />
</svg>"""

// --- ViewModel ---

class MainViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _trafficSigns = MutableStateFlow<Map<String, List<TrafficSign>>>(
        mapOf(
            "mandatory" to DefaultTrafficSigns.mandatory,
            "warning" to DefaultTrafficSigns.warning,
            "info" to DefaultTrafficSigns.info
        )
    )
    val trafficSigns: StateFlow<Map<String, List<TrafficSign>>> = _trafficSigns.asStateFlow()

    private val _appSettings = MutableStateFlow(AppSettings())
    val appSettings: StateFlow<AppSettings> = _appSettings.asStateFlow()

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()

    // Exam States
    private val _activeExamType = MutableStateFlow("mandatory")
    val activeExamType: StateFlow<String> = _activeExamType.asStateFlow()

    private val _examQuestions = MutableStateFlow<List<TrafficSign>>(emptyList())
    val examQuestions: StateFlow<List<TrafficSign>> = _examQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _lives = MutableStateFlow(3)
    val lives: StateFlow<Int> = _lives.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount: StateFlow<Int> = _correctCount.asStateFlow()

    private val _wrongCount = MutableStateFlow(0)
    val wrongCount: StateFlow<Int> = _wrongCount.asStateFlow()

    private val _selectedOption = MutableStateFlow<TrafficSign?>(null)
    val selectedOption: StateFlow<TrafficSign?> = _selectedOption.asStateFlow()

    private val _optionsList = MutableStateFlow<List<TrafficSign>>(emptyList())
    val optionsList: StateFlow<List<TrafficSign>> = _optionsList.asStateFlow()

    private val _optionsLocked = MutableStateFlow(false)
    val optionsLocked: StateFlow<Boolean> = _optionsLocked.asStateFlow()

    private val _examAlert = MutableStateFlow<Pair<String, Boolean>?>(null)
    val examAlert: StateFlow<Pair<String, Boolean>?> = _examAlert.asStateFlow()

    private var firebaseDatabase: FirebaseDatabase? = null
    private var isFirebaseInitialized = false

    fun initFirebaseAndSync(context: Context) {
        if (isFirebaseInitialized) return

        val options = FirebaseOptions.Builder()
            .setApiKey("AIzaSyBGMyVVvXZhaDLL9FOqdoF33NH_m6oByeg")
            .setApplicationId("1:651763265731:web:989b2295cf6070a1b5b276")
            .setDatabaseUrl("https://brta-88048-default-rtdb.asia-southeast1.firebasedatabase.app")
            .setProjectId("brta-88048")
            .build()

        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context, options)
            }
            firebaseDatabase = FirebaseDatabase.getInstance()
            isFirebaseInitialized = true
            setupDatabaseListeners(context)
            trackUserSession(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupDatabaseListeners(context: Context) {
        val db = firebaseDatabase ?: return

        // 1. App Settings Listener
        db.getReference("app_settings").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val qLength = snapshot.child("quiz_length").getValue(Long::class.java)?.toInt() ?: 10
                val notice = snapshot.child("app_notice").getValue(String::class.java) ?: ""
                val purpose = snapshot.child("app_purpose").getValue(String::class.java) ?: "উদ্দেশ্য: সম্মানিত চালক ভাইদের ট্রাফিক সাইন পরিচিতি ও লাইসেন্স পরীক্ষা সহজ করে ড্রাইভিং দক্ষতা বৃদ্ধির লক্ষ্যে এই অ্যাপটি তৈরি করেছেন:"
                val devName = snapshot.child("developer_name").getValue(String::class.java) ?: "Hafez Shakil Ahmed"
                val devPhone = snapshot.child("developer_phone").getValue(String::class.java) ?: ""
                val devEmail = snapshot.child("developer_email").getValue(String::class.java) ?: ""
                val fbLink = snapshot.child("facebook_link").getValue(String::class.java) ?: "https://www.facebook.com/share/1EEjYNnJCD/"

                _appSettings.value = AppSettings(
                    quizLength = qLength,
                    appNotice = notice,
                    appPurpose = purpose,
                    developerName = devName,
                    developerPhone = devPhone,
                    developerEmail = devEmail,
                    facebookLink = fbLink
                )
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        // 2. Traffic Signs Listener
        db.getReference("traffic_signs").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) return
                val signsList = mutableListOf<TrafficSign>()
                for (child in snapshot.children) {
                    val id = child.child("id").getValue(Long::class.java) ?: 0L
                    val name = child.child("name").getValue(String::class.java) ?: ""
                    val category = child.child("category").getValue(String::class.java) ?: ""
                    var signStr = child.child("sign").getValue(String::class.java) ?: ""
                    val opt1 = child.child("opt1").getValue(String::class.java)
                    val opt2 = child.child("opt2").getValue(String::class.java)
                    val opt3 = child.child("opt3").getValue(String::class.java)

                    if (signStr.isBlank()) {
                        val defaultList = when (category) {
                            "mandatory" -> DefaultTrafficSigns.mandatory
                            "warning" -> DefaultTrafficSigns.warning
                            "info" -> DefaultTrafficSigns.info
                            else -> emptyList()
                        }
                        val matched = defaultList.find { it.id == id } ?: defaultList.find { it.name == name }
                        if (matched != null) {
                            signStr = matched.sign
                        }
                    }

                    if (name.isNotEmpty() && category.isNotEmpty()) {
                        signsList.add(
                            TrafficSign(
                                id = id,
                                name = name,
                                category = category,
                                sign = signStr,
                                opt1 = opt1,
                                opt2 = opt2,
                                opt3 = opt3
                            )
                        )
                    }
                }

                if (signsList.isNotEmpty()) {
                    val grouped = signsList.groupBy { it.category }
                    _trafficSigns.value = mapOf(
                        "mandatory" to (grouped["mandatory"] ?: emptyList()),
                        "warning" to (grouped["warning"] ?: emptyList()),
                        "info" to (grouped["info"] ?: emptyList())
                    )
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        // 3. Notifications Listener
        db.getReference("notifications").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notifList = mutableListOf<NotificationItem>()
                val readIds = getReadNotificationIds(context)
                for (child in snapshot.children) {
                    val id = child.key ?: ""
                    val title = child.child("title").getValue(String::class.java) ?: "Admin"
                    val message = child.child("message").getValue(String::class.java) ?: ""
                    val link = child.child("link").getValue(String::class.java)
                    val timestamp = child.child("timestamp").getValue(Long::class.java) ?: 0L
                    val readCount = child.child("read_count").getValue(Long::class.java) ?: 0L
                    val linkClicks = child.child("link_clicks").getValue(Long::class.java) ?: 0L

                    notifList.add(
                        NotificationItem(
                            id = id,
                            title = title,
                            message = message,
                            link = link,
                            timestamp = timestamp,
                            read = readIds.contains(id),
                            readCount = readCount,
                            linkClicks = linkClicks
                        )
                    )
                }

                if (notifList.isEmpty()) {
                    // Fallback local notifications so the screen is never blank
                    notifList.add(
                        NotificationItem(
                            id = "welcome_notif",
                            title = "স্বাগতম বিআরটিএ ট্রাফিক সাইন মাস্টার অ্যাপে!",
                            message = "ট্রাফিক সাইন পরিচিতি এবং পরীক্ষা দেওয়ার জন্য অ্যাপটি সম্পূর্ণ প্রস্তুত। সকল সাইন ভালোভাবে পড়ে ক্যাটাগরি ভিত্তিক পরীক্ষা দিন।",
                            timestamp = System.currentTimeMillis() - 600000,
                            read = readIds.contains("welcome_notif")
                        )
                    )
                    notifList.add(
                        NotificationItem(
                            id = "exam_tip_notif",
                            title = "ফাইনাল মক টেস্ট টিপস 💡",
                            message = "ফাইনাল মক টেস্টে অংশ নেওয়ার আগে নিশ্চিত হোন যে আপনি সবগুলো বাধ্যতামূলক ও সতর্কতামূলক সাইন ভালোভাবে মুখস্থ করেছেন।",
                            timestamp = System.currentTimeMillis() - 3600000,
                            read = readIds.contains("exam_tip_notif")
                        )
                    )
                }

                val sortedList = notifList.sortedByDescending { it.timestamp }
                val previousList = _notifications.value
                _notifications.value = sortedList
                _unreadCount.value = sortedList.count { !it.read }

                // Trigger real system notifications for new unread items
                if (previousList.isNotEmpty()) {
                    for (notif in sortedList) {
                        if (!notif.read && previousList.none { it.id == notif.id }) {
                            triggerSystemNotification(context, notif.title, notif.message)
                        }
                    }
                } else if (sortedList.isNotEmpty()) {
                    // On first app load, if there's a very recent unread notification (less than 1 hour old), show it in status bar
                    val oneHourAgo = System.currentTimeMillis() - 3600_000
                    for (notif in sortedList) {
                        if (!notif.read && notif.timestamp > oneHourAgo) {
                            triggerSystemNotification(context, notif.title, notif.message)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun trackUserSession(context: Context) {
        val sharedPrefs = context.getSharedPreferences("brta_prefs", Context.MODE_PRIVATE)
        var sessionId = sharedPrefs.getString("brta_session_id", null)
        val now = System.currentTimeMillis()
        var firstSeen = sharedPrefs.getLong("brta_first_seen", 0L)

        if (sessionId == null) {
            sessionId = "user_" + now + "_" + UUID.randomUUID().toString().take(6)
            firstSeen = now
            sharedPrefs.edit()
                .putString("brta_session_id", sessionId)
                .putLong("brta_first_seen", firstSeen)
                .apply()
        }

        val db = firebaseDatabase ?: return
        db.getReference("user_sessions/$sessionId").setValue(
            mapOf(
                "last_seen" to now,
                "first_seen" to firstSeen
            )
        )
    }

    private fun getReadNotificationIds(context: Context): Set<String> {
        val sharedPrefs = context.getSharedPreferences("brta_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet("brta_read_notif_ids", emptySet()) ?: emptySet()
    }

    private fun saveReadNotificationIds(context: Context, ids: Set<String>) {
        val sharedPrefs = context.getSharedPreferences("brta_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putStringSet("brta_read_notif_ids", ids).apply()
    }

    fun markNotificationRead(context: Context, id: String) {
        val currentList = _notifications.value
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1 && !currentList[index].read) {
            val updated = currentList.toMutableList()
            updated[index] = updated[index].copy(read = true)
            _notifications.value = updated

            val readIds = getReadNotificationIds(context).toMutableSet()
            readIds.add(id)
            saveReadNotificationIds(context, readIds)

            firebaseDatabase?.getReference("notifications/$id/read_count")?.runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    val currentVal = mutableData.getValue(Long::class.java) ?: 0L
                    mutableData.value = currentVal + 1
                    return Transaction.success(mutableData)
                }
                override fun onComplete(error: DatabaseError?, committed: Boolean, currentData: DataSnapshot?) {}
            })
            _unreadCount.value = updated.count { !it.read }
        }
    }

    fun markAllNotificationsRead(context: Context) {
        val currentList = _notifications.value
        val updated = currentList.map { it.copy(read = true) }
        _notifications.value = updated

        val readIds = updated.map { it.id }.toSet()
        saveReadNotificationIds(context, readIds)
        _unreadCount.value = 0
    }

    fun trackLinkClick(id: String) {
        val db = firebaseDatabase ?: return
        db.getReference("notifications/$id/link_clicks").runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val currentVal = mutableData.getValue(Long::class.java) ?: 0L
                mutableData.value = currentVal + 1
                return Transaction.success(mutableData)
            }
            override fun onComplete(error: DatabaseError?, committed: Boolean, currentData: DataSnapshot?) {}
        })
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // --- Quiz Logic ---

    fun startExam(type: String) {
        _activeExamType.value = type
        _lives.value = 3
        _correctCount.value = 0
        _wrongCount.value = 0
        _currentQuestionIndex.value = 0
        _selectedOption.value = null
        _optionsLocked.value = false
        _examAlert.value = null

        val allSigns = trafficSigns.value
        val signsPool = if (type == "final") {
            (allSigns["mandatory"] ?: emptyList()) +
                    (allSigns["warning"] ?: emptyList()) +
                    (allSigns["info"] ?: emptyList())
        } else {
            allSigns[type] ?: emptyList()
        }

        val shuffled = signsPool.shuffled()
        _examQuestions.value = shuffled.take(appSettings.value.quizLength)
        loadQuestion()
        navigateTo(AppScreen.EXAM)
    }

    fun loadQuestion() {
        _selectedOption.value = null
        _optionsLocked.value = false
        _examAlert.value = null

        val questions = _examQuestions.value
        val index = _currentQuestionIndex.value
        if (index >= questions.size) return

        val currentQuestion = questions[index]

        // Options calculation
        val allSignsList = (trafficSigns.value["mandatory"] ?: emptyList()) +
                (trafficSigns.value["warning"] ?: emptyList()) +
                (trafficSigns.value["info"] ?: emptyList())

        val customWrongs = listOfNotNull(currentQuestion.opt1, currentQuestion.opt2, currentQuestion.opt3)
            .filter { it.isNotBlank() }
            .map { TrafficSign(id = -1, name = it, category = currentQuestion.category) }

        val otherSignsPool = allSignsList.filter { it.id != currentQuestion.id }.shuffled()
        val finalWrongs = (customWrongs + otherSignsPool).distinctBy { it.name }.take(2)

        _optionsList.value = (finalWrongs + currentQuestion).shuffled()
    }

    fun selectOption(option: TrafficSign) {
        if (_optionsLocked.value) return
        _selectedOption.value = option
    }

    fun handleNext() {
        val selected = _selectedOption.value
        if (selected == null) {
            _examAlert.value = Pair("⚠️ দয়া করে একটি উত্তর সিলেক্ট করুন!", false)
            return
        }

        val questions = _examQuestions.value
        val index = _currentQuestionIndex.value
        val currentQuestion = questions[index]

        if (_optionsLocked.value) {
            if (_lives.value <= 0) {
                navigateTo(AppScreen.HOME)
                return
            }

            val nextIndex = index + 1
            if (nextIndex < questions.size) {
                _currentQuestionIndex.value = nextIndex
                loadQuestion()
            } else {
                navigateTo(AppScreen.WINNER)
            }
            return
        }

        _optionsLocked.value = true
        if (selected.id == currentQuestion.id) {
            _correctCount.value = _correctCount.value + 1
            _examAlert.value = Pair("✅ চমৎকার! আপনার উত্তরটি সঠিক হয়েছে।", true)
        } else {
            _wrongCount.value = _wrongCount.value + 1
            val updatedLives = _lives.value - 1
            _lives.value = updatedLives
            if (updatedLives <= 0) {
                _examAlert.value = Pair("❌ ভুল উত্তর! আপনার ৩টি লাইফ শেষ হয়ে গেছে।", false)
            } else {
                _examAlert.value = Pair("❌ ভুল উত্তর! সঠিক উত্তর: \"${currentQuestion.name}\"", false)
            }
        }
    }
}

// --- Screen Router & Activity ---

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MainViewModel = viewModel()
                val context = LocalContext.current
                viewModel.initFirebaseAndSync(context)

                // Request Notification Permission on Android 13+ (Tiramisu and above)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { _ -> }
                    LaunchedEffect(Unit) {
                        if (androidx.core.content.ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.POST_NOTIFICATIONS
                            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
                        ) {
                            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                val currentScreen by viewModel.currentScreen.collectAsState()

                if (currentScreen != AppScreen.HOME) {
                    BackHandler {
                        viewModel.navigateTo(AppScreen.HOME)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    when (currentScreen) {
                        AppScreen.HOME -> HomeScreen(viewModel)
                        AppScreen.CATALOG -> CatalogScreen(viewModel)
                        AppScreen.EXAM -> ExamScreen(viewModel)
                        AppScreen.NOTIFICATIONS -> NotificationsScreen(viewModel)
                        AppScreen.WINNER -> WinnerScreen(viewModel)
                    }
                }
            }
        }
    }
}

// --- Helper Functions ---

fun toBanglaNum(num: Int): String {
    val banglaNums = mapOf(
        '0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪',
        '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯'
    )
    return num.toString().map { banglaNums[it] ?: it }.joinToString("")
}

fun formatBanglaTimeAgo(timestamp: Long): String {
    if (timestamp == 0L) return ""
    val diffSec = (System.currentTimeMillis() - timestamp) / 1000
    if (diffSec < 60) return "এখনই"
    val diffMin = diffSec / 60
    if (diffMin < 60) return toBanglaNum(diffMin.toInt()) + " মিনিট আগে"
    val diffHour = diffMin / 60
    if (diffHour < 24) return toBanglaNum(diffHour.toInt()) + " ঘণ্টা আগে"
    val diffDay = diffHour / 24
    if (diffDay == 1L) return "গতকাল"
    if (diffDay < 7) return toBanglaNum(diffDay.toInt()) + " দিন আগে"
    val date = Date(timestamp)
    val cal = Calendar.getInstance().apply { time = date }
    return toBanglaNum(cal.get(Calendar.DAY_OF_MONTH)) + "/" + toBanglaNum(cal.get(Calendar.MONTH) + 1) + "/" + toBanglaNum(cal.get(Calendar.YEAR))
}

// --- Svg WebView Rendering ---

@Composable
fun SvgImage(svgCode: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = false
                setBackgroundColor(0)
                isVerticalScrollBarEnabled = false
                isHorizontalScrollBarEnabled = false
                setOnTouchListener { _, _ -> true }
            }
        },
        update = { webView ->
            val html = """
                <html>
                <head>
                <style>
                body {
                    margin: 0;
                    padding: 0;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    width: 100%;
                    height: 100%;
                    background-color: transparent;
                }
                svg {
                    width: 100%;
                    height: 100%;
                }
                </style>
                </head>
                <body>
                $svgCode
                </body>
                </html>
            """.trimIndent()
            val encodedHtml = android.util.Base64.encodeToString(html.toByteArray(Charsets.UTF_8), android.util.Base64.NO_WRAP)
            webView.loadData(encodedHtml, "text/html; charset=utf-8", "base64")
        },
        modifier = modifier
    )
}

// --- Composable Screens ---

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val settings by viewModel.appSettings.collectAsState()
    val unreadCount by viewModel.unreadCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                SvgImage(
                    svgCode = APP_LOGO_SVG,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "বিআরটিএ প্রস্তুতি",
                        fontSize = 13.sp,
                        color = Color(0xFF666666),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "বিআরটিএ ট্রাফিক সাইন মাস্টার",
                        fontSize = 18.sp,
                        color = Color(0xFF111111),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { viewModel.navigateTo(AppScreen.NOTIFICATIONS) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🔔", fontSize = 20.sp)
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(Color(0xFFF42A41), shape = CircleShape)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = toBanglaNum(unreadCount),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Notice Board
            if (settings.appNotice.isNotBlank()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7)),
                        border = BorderStroke(1.dp, Color(0xFFFBC02D)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "📢 ", fontSize = 16.sp)
                            Text(
                                text = settings.appNotice,
                                fontSize = 14.sp,
                                color = Color(0xFF5D4037),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Catalog Hub Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.navigateTo(AppScreen.CATALOG) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🚦", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "ট্রাফিক সাইন পরিচিতি",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111111)
                            )
                            Text(
                                text = "সবগুলো সাইন বিস্তারিত পড়ুন",
                                fontSize = 13.sp,
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "ক্যাটাগরি ভিত্তিক টেস্ট",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF444444),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                CategoryExamCard(
                    emoji = "🔴",
                    title = "বাধ্যতামূলক সাইন টেস্ট",
                    onClick = { viewModel.startExam("mandatory") }
                )
            }

            item {
                CategoryExamCard(
                    emoji = "🟡",
                    title = "সতর্কতামূলক সাইন টেস্ট",
                    onClick = { viewModel.startExam("warning") }
                )
            }

            item {
                CategoryExamCard(
                    emoji = "🔵",
                    title = "তথ্যমূলক সাইন টেস্ট",
                    onClick = { viewModel.startExam("info") }
                )
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.startExam("final") }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🏆", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "ফাইনাল মক টেস্ট",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "সব সাইন থেকে পরীক্ষা দিন",
                                fontSize = 13.sp,
                                color = Color(0xFFE8F5E9)
                            )
                        }
                    }
                }
            }

            // Developer Footer Credit Section
            val showDeveloperSection = settings.appPurpose.isNotBlank() ||
                    settings.developerName.isNotBlank() ||
                    settings.developerPhone.isNotBlank() ||
                    settings.developerEmail.isNotBlank() ||
                    settings.facebookLink.isNotBlank()

            if (showDeveloperSection) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                        border = BorderStroke(1.dp, Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 24.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            // Left vertical green bar
                            Box(
                                modifier = Modifier
                                    .width(5.dp)
                                    .fillMaxHeight()
                                    .background(Color(0xFF2E7D32))
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (settings.appPurpose.isNotBlank()) {
                                    Text(
                                        text = settings.appPurpose,
                                        fontSize = 13.sp,
                                        color = Color(0xFF555555),
                                        textAlign = TextAlign.Center,
                                        lineHeight = 18.sp
                                    )
                                }
                                if (settings.developerName.isNotBlank()) {
                                    Text(
                                        text = settings.developerName,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                                
                                if (settings.developerPhone.isNotBlank()) {
                                    ContactBadgeButton(
                                        icon = { Text("📞", fontSize = 16.sp) },
                                        label = settings.developerPhone,
                                        textColor = Color(0xFF333333),
                                        borderColor = Color(0xFFBBDEFB),
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${settings.developerPhone}"))
                                            context.startActivity(intent)
                                        }
                                    )
                                }
                                if (settings.developerEmail.isNotBlank()) {
                                    ContactBadgeButton(
                                        icon = { Text("✉️", fontSize = 16.sp) },
                                        label = settings.developerEmail,
                                        textColor = Color(0xFF333333),
                                        borderColor = Color(0xFFBBDEFB),
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${settings.developerEmail}"))
                                            context.startActivity(intent)
                                        }
                                    )
                                }
                                if (settings.facebookLink.isNotBlank()) {
                                    ContactBadgeButton(
                                        icon = {
                                            Box(
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .background(Color(0xFF1877F2), shape = CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "f",
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.offset(y = (-1).dp)
                                                )
                                            }
                                        },
                                        label = "ফেসবুক আইডি",
                                        textColor = Color(0xFF1877F2),
                                        borderColor = Color(0xFFBBDEFB),
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(settings.facebookLink))
                                            context.startActivity(intent)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryExamCard(emoji: String, title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )
        }
    }
}

@Composable
fun ContactBadgeButton(
    icon: @Composable () -> Unit,
    label: String,
    textColor: Color = Color(0xFF333333),
    borderColor: Color = Color(0xFFE2E8F0),
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = textColor),
        border = BorderStroke(1.5.dp, borderColor),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        modifier = modifier.height(44.dp)
    ) {
        icon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// --- Catalog Screen ---

@Composable
fun CatalogScreen(viewModel: MainViewModel) {
    val trafficSigns by viewModel.trafficSigns.collectAsState()
    var openCategory by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1E88E5))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ট্রাফিক সাইন ক্যাটালগ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val categories = listOf(
                Triple("mandatory", "১. বাধ্যতামূলক সাইন", "🔴"),
                Triple("warning", "২. সতর্কতামূলক সাইন", "🟡"),
                Triple("info", "৩. তথ্যমূলক সাইন", "🔵")
            )

            categories.forEach { (catKey, catTitle, emoji) ->
                val isOpen = openCategory == catKey
                val list = trafficSigns[catKey] ?: emptyList()

                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    openCategory = if (isOpen) null else catKey
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$emoji $catTitle",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = if (isOpen) "▲" else "▼",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    }
                }

                if (isOpen) {
                    if (list.isEmpty()) {
                        item {
                            Text(
                                text = "কোনো সাইন পাওয়া যায়নি",
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(list) { sign ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .border(BorderStroke(1.dp, Color(0xFFE5E5E5)), shape = RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SvgImage(
                                    svgCode = sign.sign,
                                    modifier = Modifier.size(55.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = sign.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

// --- Notification Screen ---

@Composable
fun NotificationsScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1E88E5))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "নোটিফিকেশন",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Text(
                text = "✓ সব পড়া চিহ্নিত করুন",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                modifier = Modifier
                    .clickable { viewModel.markAllNotificationsRead(context) }
                    .padding(8.dp)
            )
        }

        if (notifications.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🔔", fontSize = 50.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "এখনো কোনো নোটিফিকেশন আসেনি",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notifications) { notif ->
                    val cardBorder = if (notif.read) null else BorderStroke(2.dp, Color(0xFF90CAF9))
                    val cardBg = if (notif.read) Color.White else Color(0xFFF3F9FF)

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        border = cardBorder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.markNotificationRead(context, notif.id) }
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(
                                        if (notif.read) Color(0xFFE3F2FD) else Color(0xFF1E88E5),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "📢", fontSize = 20.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = notif.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF222222)
                                    )
                                    Text(
                                        text = formatBanglaTimeAgo(notif.timestamp),
                                        fontSize = 11.sp,
                                        color = Color(0xFF999999)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = notif.message,
                                    fontSize = 13.5.sp,
                                    color = Color(0xFF555555),
                                    lineHeight = 18.sp
                                )

                                notif.link?.let { link ->
                                    if (link.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = {
                                                viewModel.trackLinkClick(notif.id)
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                                context.startActivity(intent)
                                            },
                                            shape = RoundedCornerShape(20.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFE3F2FD),
                                                contentColor = Color(0xFF1E88E5)
                                            ),
                                            border = BorderStroke(1.dp, Color(0xFFBBDEFB)),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text(text = "🔗 লিঙ্কটি ভিজিট করুন", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }

                            if (!notif.read) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 6.dp)
                                        .size(8.dp)
                                        .background(Color(0xFF1E88E5), shape = CircleShape)
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

// --- Quiz Exam Screen ---

@Composable
fun ExamScreen(viewModel: MainViewModel) {
    val examQuestions by viewModel.examQuestions.collectAsState()
    val index by viewModel.currentQuestionIndex.collectAsState()
    val lives by viewModel.lives.collectAsState()
    val optionsList by viewModel.optionsList.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val optionsLocked by viewModel.optionsLocked.collectAsState()
    val alertState by viewModel.examAlert.collectAsState()

    if (index >= examQuestions.size) return
    val currentQuestion = examQuestions[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
            }

            val progress = remember(index, examQuestions.size) {
                if (examQuestions.isEmpty()) 0f else (index.toFloat() / examQuestions.size.toFloat())
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    .height(12.dp)
                    .background(Color(0xFFE5E5E5), shape = RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .background(Color(0xFF4CAF50), shape = RoundedCornerShape(6.dp))
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "❤️", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = toBanglaNum(lives),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEA4335)
                )
            }
        }

        AnimatedVisibility(
            visible = alertState != null,
            enter = slideInVertically(initialOffsetY = { -80 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -80 }) + fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            alertState?.let { (msg, isSuccess) ->
                val bg = if (isSuccess) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                val borderCol = if (isSuccess) Color(0xFF66BB6A) else Color(0xFFEF5350)
                val textCol = if (isSuccess) Color(0xFF2E7D32) else Color(0xFFC62828)

                Card(
                    colors = CardDefaults.cardColors(containerColor = bg),
                    border = BorderStroke(2.dp, borderCol),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = msg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textCol,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "প্রশ্ন ${toBanglaNum(index + 1)}/${toBanglaNum(examQuestions.size)}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "এই সাইনটি দ্বারা কী বোঝানো হয়?",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .height(120.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                SvgImage(
                    svgCode = currentQuestion.sign,
                    modifier = Modifier.size(90.dp)
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(optionsList.size) { i ->
                    val option = optionsList[i]
                    val isSelected = selectedOption == option

                    val borderStroke: BorderStroke
                    val containerBg: Color
                    val textCol: Color

                    if (optionsLocked) {
                        val isCorrect = option.id == currentQuestion.id
                        if (isCorrect) {
                            borderStroke = BorderStroke(2.dp, Color(0xFF4CAF50))
                            containerBg = Color(0xFFE8F5E9)
                            textCol = Color(0xFF2E7D32)
                        } else if (isSelected) {
                            borderStroke = BorderStroke(2.dp, Color(0xFFF44336))
                            containerBg = Color(0xFFFFEBEE)
                            textCol = Color(0xFFC62828)
                        } else {
                            borderStroke = BorderStroke(2.dp, Color(0xFFE5E5E5))
                            containerBg = Color.White
                            textCol = Color.Black
                        }
                    } else {
                        if (isSelected) {
                            borderStroke = BorderStroke(2.dp, Color(0xFF1E88E5))
                            containerBg = Color(0xFFE3F2FD)
                            textCol = Color(0xFF1E88E5)
                        } else {
                            borderStroke = BorderStroke(2.dp, Color(0xFFE5E5E5))
                            containerBg = Color.White
                            textCol = Color.Black
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = containerBg),
                        border = borderStroke,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectOption(option) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .border(2.dp, Color(0xFFDDDDDD), shape = RoundedCornerShape(5.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = toBanglaNum(i + 1),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = option.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = textCol
                            )
                        }
                    }
                }
            }
        }

        val btnLabel = if (optionsLocked) "পরবর্তী প্রশ্ন" else "যাচাই করুন"
        val btnColor = if (optionsLocked) Color(0xFF1E88E5) else Color(0xFF4CAF50)

        Button(
            onClick = { viewModel.handleNext() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = btnColor, contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(50.dp)
        ) {
            Text(text = btnLabel, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- Winner / Result Screen ---

@Composable
fun WinnerScreen(viewModel: MainViewModel) {
    val correctCount by viewModel.correctCount.collectAsState()
    val wrongCount by viewModel.wrongCount.collectAsState()

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🏆",
                    fontSize = 80.sp,
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                    lineHeight = 90.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "অভিনন্দন!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "আপনি সফলভাবে পরীক্ষা সম্পন্ন করেছেন! 🎉",
                    fontSize = 15.sp,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF9F9F9), shape = RoundedCornerShape(12.dp))
                        .border(1.5.dp, Color(0xFFE5E5E5), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "সঠিক উত্তর", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = toBanglaNum(correctCount),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.5.dp)
                            .height(50.dp)
                            .background(Color(0xFFE5E5E5))
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "ভুল উত্তর", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = toBanglaNum(wrongCount),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF44336)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.navigateTo(AppScreen.HOME) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "প্রধান মেনু", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "বিআরটিএ ট্রাফিক সাইন মাস্টার"
        val descriptionText = "গুরুত্বপূর্ণ নোটিফিকেশন ও আপডেট"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("brta_notifications", name, importance).apply {
            description = descriptionText
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun triggerSystemNotification(context: Context, title: String, message: String) {
    try {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, "brta_notifications")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
