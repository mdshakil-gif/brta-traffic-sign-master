package com.example

data class TrafficSign(
    val id: Long = 0,
    val name: String = "",
    val category: String = "",
    val sign: String = "", // SVG string
    val opt1: String? = null,
    val opt2: String? = null,
    val opt3: String? = null
)

object DefaultTrafficSigns {
    val mandatory = listOf(
        TrafficSign(
            id = 1,
            name = "থামুন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="30,10 70,10 90,30 90,70 70,90 30,90 10,70 10,30" fill="#E53935" /><text x="50" y="58" fill="white" font-size="20" font-weight="bold" text-anchor="middle">থামুন</text></svg>"""
        ),
        TrafficSign(
            id = 2,
            name = "রাস্তা দিন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="10,15 90,15 50,85" fill="white" stroke="#E53935" stroke-width="10" stroke-linejoin="round"/></svg>"""
        ),
        TrafficSign(
            id = 3,
            name = "প্রবেশ নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#E53935"/><rect x="18" y="44" width="64" height="12" fill="white" rx="2"/></svg>"""
        ),
        TrafficSign(
            id = 4,
            name = "মোটরযান চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(18, 30) scale(0.6)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Q62,12 65,18 L70,30 Q78,32 78,40 L78,52 C78,55 75,55 75,55 L75,60 C75,62 70,62 70,60 L70,55 L10,55 L10,60 C10,62 5,62 5,60 L5,55 Z" fill="black"/><circle cx="15" cy="45" r="5" fill="white"/><circle cx="65" cy="45" r="5" fill="white"/></g><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 5,
            name = "ট্রাক চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(20, 32) scale(0.6)"><rect x="10" y="20" width="45" height="25" fill="black"/><rect x="55" y="25" width="25" height="20" fill="black"/><circle cx="20" cy="48" r="8" fill="black"/><circle cx="65" cy="48" r="8" fill="black"/></g><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 6,
            name = "ঠেলাগাড়ি চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(20, 30) scale(0.8)"><line x1="10" y1="30" x2="50" y2="30" stroke="black" stroke-width="5"/><line x1="50" y1="30" x2="65" y2="15" stroke="black" stroke-width="5"/><circle cx="30" cy="45" r="10" fill="none" stroke="black" stroke-width="5"/></g><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 7,
            name = "পশুচালিত গাড়ি চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="60" font-size="34" text-anchor="middle" dominant-baseline="middle">🐎</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 8,
            name = "পথচারী চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="58" font-size="36" text-anchor="middle" dominant-baseline="middle">🚶</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 9,
            name = "রিকশা চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(15, 25) scale(0.7)"><circle cx="20" cy="50" r="12" fill="none" stroke="black" stroke-width="3"/><circle cx="65" cy="50" r="12" fill="none" stroke="black" stroke-width="3"/><circle cx="85" cy="50" r="12" fill="none" stroke="black" stroke-width="3"/><path d="M20,50 L40,25 L65,50 M40,25 L85,50 M30,15 L50,15" fill="none" stroke="black" stroke-width="4"/></g><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 10,
            name = "সাইকেল চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="56" font-size="36" text-anchor="middle" dominant-baseline="middle">🚲</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 11,
            name = "ধীরগতির যানবাহন চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="58" font-size="36" text-anchor="middle" dominant-baseline="middle">🚜</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 12,
            name = "বিস্ফোরক দ্রব্যবাহী যানবাহন চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="58" font-size="36" text-anchor="middle" dominant-baseline="middle">💥</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 13,
            name = "প্রদর্শিত দৈর্ঘ্যসীমার ঊর্ধ্বের যানবাহন চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g><text x="50" y="54" font-size="20" font-weight="bold" fill="black" text-anchor="middle" dominant-baseline="middle">10m</text><path d="M15,50 L25,44 L25,56 Z" fill="black"/><path d="M85,50 L75,44 L75,56 Z" fill="black"/><line x1="22" y1="50" x2="78" y2="50" stroke="black" stroke-width="2"/></g></svg>"""
        ),
        TrafficSign(
            id = 14,
            name = "প্রদর্শিত উচ্চতা সীমার ঊর্ধ্বের যানবাহন চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g><text x="50" y="52" font-size="18" font-weight="bold" fill="black" text-anchor="middle" dominant-baseline="middle">4.4m</text><path d="M50,15 L44,25 L56,25 Z" fill="black"/><path d="M50,85 L44,75 L56,75 Z" fill="black"/><line x1="50" y1="22" x2="50" y2="78" stroke="black" stroke-width="2"/></g></svg>"""
        ),
        TrafficSign(
            id = 15,
            name = "প্রদর্শিত প্রস্থ সীমার ঊর্ধ্বের যানবাহন চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g><text x="50" y="52" font-size="18" font-weight="bold" fill="black" text-anchor="middle" dominant-baseline="middle">2.4m</text><path d="M15,50 L25,44 L25,56 Z" fill="black"/><path d="M85,50 L75,44 L75,56 Z" fill="black"/><line x1="22" y1="50" x2="78" y2="50" stroke="black" stroke-width="2"/></g></svg>"""
        ),
        TrafficSign(
            id = 16,
            name = "প্রদর্শিত ওজন সীমার ঊর্ধ্বের যানবাহন চলাচল নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="54" font-size="24" font-weight="bold" fill="black" text-anchor="middle" dominant-baseline="middle">17T</text></svg>"""
        ),
        TrafficSign(
            id = 17,
            name = "এক্সেল ওজনের সর্বোচ্চ সীমা",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(15, 30) scale(0.7)"><line x1="10" y1="30" x2="90" y2="30" stroke="black" stroke-width="6"/><circle cx="25" cy="30" r="12" fill="none" stroke="black" stroke-width="4"/><circle cx="75" cy="30" r="12" fill="none" stroke="black" stroke-width="4"/></g><text x="50" y="72" font-size="20" font-weight="bold" fill="black" text-anchor="middle">4T</text></svg>"""
        ),
        TrafficSign(
            id = 18,
            name = "পার্কিং নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5" stroke="#E53935" stroke-width="8"/><text x="50" y="58" font-size="44" font-weight="bold" fill="white" text-anchor="middle" dominant-baseline="middle">P</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 19,
            name = "থামানো নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5" stroke="#E53935" stroke-width="8"/><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/><line x1="80" y1="20" x2="20" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 20,
            name = "ওভারটেকিং নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(18, 38) scale(0.4)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Q62,12 65,18 L70,30 Q78,32 78,40 L78,52 C78,55 75,55 75,55 L75,60 L70,55 L10,55 L10,60 Z" fill="black"/></g><g transform="translate(48, 38) scale(0.4)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Q62,12 65,18 L70,30 Q78,32 78,40 L78,52 C78,55 75,55 75,55 L75,60 L70,55 L10,55 L10,60 Z" fill="#E53935"/></g></svg>"""
        ),
        TrafficSign(
            id = 21,
            name = "না থেমে চলা নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><rect x="15" y="44" width="70" height="12" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 22,
            name = "বামে মোড় নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><path d="M65,70 L65,50 Q65,40 55,40 L35,40" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><path d="M38,30 L25,40 L38,50 Z" fill="black"/><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 23,
            name = "ডানে মোড় নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><path d="M35,70 L35,50 Q35,40 45,40 L65,40" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><path d="M62,30 L75,40 L62,50 Z" fill="black"/><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 24,
            name = "ইউ-টার্ন নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><path d="M65,70 L65,45 C65,30 35,30 35,45 L35,60" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><path d="M25,55 L35,70 L45,55 Z" fill="black"/><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 25,
            name = "হর্ন বাজানো নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="56" font-size="36" text-anchor="middle" dominant-baseline="middle">📢</text><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 26,
            name = "সর্বোচ্চ গতিসীমা",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><text x="50" y="58" font-size="32" font-weight="bold" fill="black" text-anchor="middle" dominant-baseline="middle">40</text></svg>"""
        ),
        TrafficSign(
            id = 27,
            name = "গতিসীমা বাধানিষেধের শেষ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#9E9E9E" stroke-width="4"/><line x1="20" y1="80" x2="80" y2="20" stroke="#9E9E9E" stroke-width="8"/><line x1="25" y1="80" x2="80" y2="25" stroke="#9E9E9E" stroke-width="2"/><line x1="15" y1="80" x2="80" y2="15" stroke="#9E9E9E" stroke-width="2"/></svg>"""
        ),
        TrafficSign(
            id = 28,
            name = "সাময়িক 'থামার' চিহ্ন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#E53935"/></svg>"""
        ),
        TrafficSign(
            id = 29,
            name = "সাময়িক 'চলাচল' চিহ্ন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#2E7D32"/></svg>"""
        ),
        TrafficSign(
            id = 30,
            name = "বাধ্যবাধকতা শেষ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="black" stroke-width="4"/><line x1="20" y1="80" x2="80" y2="20" stroke="black" stroke-width="8"/></svg>"""
        ),
        TrafficSign(
            id = 31,
            name = "শুধু সামনে চলুন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></svg>"""
        ),
        TrafficSign(
            id = 32,
            name = "শুধু বাম দিকে চলাচল",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><g transform="rotate(-90 50 50)"><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></g></svg>"""
        ),
        TrafficSign(
            id = 33,
            name = "শুধু ডান দিকে চলাচল",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><g transform="rotate(90 50 50)"><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></g></svg>"""
        ),
        TrafficSign(
            id = 34,
            name = "বামপাশ দিয়ে চলুন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><g transform="rotate(-135 50 50)"><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></g></svg>"""
        ),
        TrafficSign(
            id = 35,
            name = "ডানপাশ দিয়ে চলুন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><g transform="rotate(135 50 50)"><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></g></svg>"""
        ),
        TrafficSign(
            id = 36,
            name = "সামনে এগিয়ে বামে মোড় দিন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><path d="M45,75 L45,50 A15,15 0 0,0 30,35 L15,35" fill="none" stroke="white" stroke-width="8"/><polygon points="15,25 5,35 15,45" fill="white"/></svg>"""
        ),
        TrafficSign(
            id = 37,
            name = "সামনে এগিয়ে ডানে মোড় দিন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><path d="M55,75 L55,50 A15,15 0 0,1 70,35 L85,35" fill="none" stroke="white" stroke-width="8"/><polygon points="85,25 95,35 85,45" fill="white"/></svg>"""
        ),
        TrafficSign(
            id = 38,
            name = "ছোট গোল চক্কর",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><path d="M50,22 A28,28 0 0,1 78,50 M78,50 A28,28 0 0,1 50,78 M50,78 A28,28 0 0,1 22,50 M22,50 A28,28 0 0,1 50,22" fill="none" stroke="white" stroke-width="6"/><polygon points="54,14 64,22 54,30" fill="white" transform="rotate(30 50 50)"/><polygon points="54,14 64,22 54,30" fill="white" transform="rotate(150 50 50)"/><polygon points="54,14 64,22 54,30" fill="white" transform="rotate(270 50 50)"/></svg>"""
        ),
        TrafficSign(
            id = 39,
            name = "যেকোনো একদিকে যেতে পারেন",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><g transform="rotate(-45 50 50)"><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></g><g transform="rotate(45 50 50)"><path d="M50,20 L30,45 L42,45 L42,80 L58,80 L58,45 L70,45 Z" fill="white"/></g></svg>"""
        ),
        TrafficSign(
            id = 40,
            name = "একদিকে চলাচল",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="15" y="5" width="70" height="90" rx="10" fill="#1E88E5"/><path d="M50,15 L25,45 L40,45 L40,85 L60,85 L60,45 L75,45 Z" fill="white"/></svg>"""
        ),
        TrafficSign(
            id = 41,
            name = "সাইকেল ও পথচারী পথ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><text x="50" y="38" font-size="28" text-anchor="middle" dominant-baseline="middle">🚲</text><text x="50" y="68" font-size="28" text-anchor="middle" dominant-baseline="middle">🚶</text></svg>"""
        ),
        TrafficSign(
            id = 42,
            name = "একমুখী চলার রাস্তা",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><path d="M35,20 L20,40 L30,40 L30,80 L40,80 L40,40 L50,40 Z" fill="white"/><path d="M65,80 L80,60 L70,60 L70,20 L60,20 L60,60 L50,60 Z" fill="white"/></svg>"""
        ),
        TrafficSign(
            id = 43,
            name = "শুধু রিকশা চলাচলের রাস্তা",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><g transform="translate(15, 25) scale(0.7)"><circle cx="20" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><circle cx="65" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><circle cx="85" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><path d="M20,50 L40,25 L65,50 M40,25 L85,50 M30,15 L50,15" fill="none" stroke="white" stroke-width="4"/></g></svg>"""
        ),
        TrafficSign(
            id = 44,
            name = "শুধু সাইকেল চলাচলের রাস্তা",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5"/><text x="50" y="52" font-size="38" text-anchor="middle" dominant-baseline="middle">🚲</text></svg>"""
        ),
        TrafficSign(
            id = 110,
            name = "নূন্যতম গতিসীমা",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="#1E88E5" stroke="white" stroke-width="2"/><text x="50" y="58" font-size="32" font-weight="bold" fill="white" text-anchor="middle" dominant-baseline="middle">30</text></svg>"""
        ),
        TrafficSign(
            id = 111,
            name = "বাম দিকে ওভারটেকিং নিষেধ",
            category = "mandatory",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><circle cx="50" cy="50" r="42" fill="white" stroke="#E53935" stroke-width="8"/><g transform="translate(18, 38) scale(0.4)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Q62,12 65,18 L70,30 Q78,32 78,40 L78,52 C78,55 75,55 75,55 L75,60 L70,55 L10,55 L10,60 Z" fill="#E53935"/></g><g transform="translate(48, 38) scale(0.4)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Q62,12 65,18 L70,30 Q78,32 78,40 L78,52 C78,55 75,55 75,55 L75,60 L70,55 L10,55 L10,60 Z" fill="black"/></g><line x1="20" y1="20" x2="80" y2="80" stroke="#E53935" stroke-width="8"/></svg>"""
        )
    )

    val warning = listOf(
        TrafficSign(
            id = 45,
            name = "দুই রাস্তার সংযোগস্থল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="35" x2="50" y2="75" stroke="black" stroke-width="12" stroke-linecap="round"/><line x1="32" y1="48" x2="50" y2="48" stroke="black" stroke-width="8" stroke-linecap="round"/><line x1="50" y1="62" x2="68" y2="62" stroke="black" stroke-width="8" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 46,
            name = "সম্মুখে প্রধান সড়ক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="30" x2="50" y2="80" stroke="black" stroke-width="14" stroke-linecap="round"/><line x1="28" y1="55" x2="72" y2="55" stroke="black" stroke-width="6" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 47,
            name = "পার্শ্ব রাস্তা",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="30" x2="50" y2="80" stroke="black" stroke-width="14" stroke-linecap="round"/><line x1="50" y1="55" x2="72" y2="55" stroke="black" stroke-width="10" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 48,
            name = "প্রধান সড়ক সংযোগস্থল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="30" x2="50" y2="75" stroke="black" stroke-width="10" stroke-linecap="round"/><line x1="28" y1="52" x2="72" y2="52" stroke="black" stroke-width="10" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 49,
            name = "'টি' সংযোগস্থল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="45" x2="50" y2="75" stroke="black" stroke-width="12" stroke-linecap="round"/><line x1="26" y1="45" x2="74" y2="45" stroke="black" stroke-width="12" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 50,
            name = "'ওয়াই' সংযোগস্থল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="58" x2="50" y2="78" stroke="black" stroke-width="12" stroke-linecap="round"/><line x1="50" y1="58" x2="30" y2="38" stroke="black" stroke-width="12" stroke-linecap="round"/><line x1="50" y1="58" x2="70" y2="38" stroke="black" stroke-width="12" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 51,
            name = "বাম দিক থেকে যানবাহন আসছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="30" x2="50" y2="80" stroke="black" stroke-width="14" stroke-linecap="round"/><line x1="50" y1="58" x2="28" y2="44" stroke="black" stroke-width="10" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 52,
            name = "ডান দিক থেকে যানবাহন আসছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><line x1="50" y1="30" x2="50" y2="80" stroke="black" stroke-width="14" stroke-linecap="round"/><line x1="50" y1="58" x2="72" y2="44" stroke="black" stroke-width="10" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 53,
            name = "গোল চক্কর",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M50,38 A16,16 0 1,1 34,54" fill="none" stroke="black" stroke-width="6" stroke-linecap="round"/><path d="M50,70 A16,16 0 1,1 66,54" fill="none" stroke="black" stroke-width="6" stroke-linecap="round"/><polygon points="44,32 54,38 44,44" fill="black"/><polygon points="56,76 46,70 56,64" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 54,
            name = "ডানে আঁকাবাঁকা মোড়",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M40,75 L40,62 Q40,48 55,48 T70,35" fill="none" stroke="black" stroke-width="10" stroke-linecap="round"/><polygon points="70,25 80,35 65,40" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 55,
            name = "বামে আঁকাবাঁকা মোড়",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M60,75 L60,62 Q60,48 45,48 T30,35" fill="none" stroke="black" stroke-width="10" stroke-linecap="round"/><polygon points="30,25 20,35 35,40" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 56,
            name = "ডানে খাঁড়া বাঁক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M35,75 L35,45 L65,45" fill="none" stroke="black" stroke-width="12" stroke-linecap="round" stroke-linejoin="round"/><polygon points="60,35 75,45 60,55" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 57,
            name = "বামে খাঁড়া বাঁক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M65,75 L65,45 L35,45" fill="none" stroke="black" stroke-width="12" stroke-linecap="round" stroke-linejoin="round"/><polygon points="40,35 25,45 40,55" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 58,
            name = "ডানে মোড়",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M40,75 C40,55 50,45 65,45" fill="none" stroke="black" stroke-width="10" stroke-linecap="round"/><polygon points="60,35 75,45 60,55" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 59,
            name = "বামে মোড়",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M60,75 C60,55 50,45 35,45" fill="none" stroke="black" stroke-width="10" stroke-linecap="round"/><polygon points="40,35 25,45 40,55" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 60,
            name = "উভয় পার্শ্ব থেকে সড়ক সরু হচ্ছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M32,75 L32,60 L44,45 L44,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><path d="M68,75 L68,60 L56,45 L56,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 61,
            name = "ডান দিক থেকে সড়ক সরু হচ্ছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M35,75 L35,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><path d="M65,75 L65,60 L50,45 L50,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 62,
            name = "বাম দিক থেকে সড়ক সরু হচ্ছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M35,75 L35,60 L50,45 L50,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><path d="M65,75 L65,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 63,
            name = "দুটি পৃথক সড়ক সামনে মিলিত হয়েছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M32,75 L42,50 L42,30 M68,75 L58,50 L58,30" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/><rect x="46" y="30" width="8" height="45" fill="black" rx="2"/></svg>"""
        ),
        TrafficSign(
            id = 64,
            name = "ট্রাফিক সিগন্যাল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><rect x="40" y="32" width="20" height="46" rx="6" fill="#333"/><circle cx="50" cy="40" r="6" fill="#E53935"/><circle cx="50" cy="55" r="6" fill="#FFB300"/><circle cx="50" cy="70" r="6" fill="#2E7D32"/></svg>"""
        ),
        TrafficSign(
            id = 65,
            name = "অত্যধিক ঢালু নিম্নাভিমুখী সড়ক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><polygon points="25,75 75,75 75,45" fill="black"/><g transform="translate(45, 45) rotate(31) scale(0.35)"><rect x="10" y="20" width="45" height="25" fill="black"/><circle cx="20" cy="48" r="8" fill="black"/><circle cx="45" cy="48" r="8" fill="black"/></g></svg>"""
        ),
        TrafficSign(
            id = 66,
            name = "অত্যধিক ঢালু ঊর্ধ্বমুখী সড়ক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><polygon points="25,75 75,75 25,45" fill="black"/><g transform="translate(30, 42) rotate(-31) scale(0.35)"><rect x="10" y="20" width="45" height="25" fill="black"/><circle cx="20" cy="48" r="8" fill="black"/><circle cx="45" cy="48" r="8" fill="black"/></g></svg>"""
        ),
        TrafficSign(
            id = 67,
            name = "রাস্তা পিছল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M30,75 C35,60 45,75 55,65 C60,55 70,70 75,60" fill="none" stroke="black" stroke-width="4"/><g transform="translate(35, 38) scale(0.4)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Z" fill="black"/></g></svg>"""
        ),
        TrafficSign(
            id = 68,
            name = "শিথিল নুড়ি-পাথর",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><g transform="translate(25, 42) scale(0.4)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 Z" fill="black"/></g><circle cx="68" cy="65" r="3" fill="black"/><circle cx="74" cy="58" r="2" fill="black"/><circle cx="78" cy="67" r="2" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 69,
            name = "শিলা পতন",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><polygon points="25,78 40,42 55,78" fill="black"/><circle cx="60" cy="48" r="4" fill="black"/><circle cx="66" cy="58" r="3" fill="black"/><circle cx="58" cy="68" r="3" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 70,
            name = "পথচারী পারাপার",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><g transform="translate(20, 68)"><rect x="0" y="0" width="60" height="4" fill="black"/><rect x="5" y="4" width="8" height="4" fill="black"/><rect x="20" y="4" width="8" height="4" fill="black"/><rect x="35" y="4" width="8" height="4" fill="black"/><rect x="50" y="4" width="8" height="4" fill="black"/></g><text x="50" y="52" font-size="28" text-anchor="middle" dominant-baseline="middle">🚶</text></svg>"""
        ),
        TrafficSign(
            id = 71,
            name = "সামনে স্কুল",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="34" text-anchor="middle" dominant-baseline="middle">🚸</text></svg>"""
        ),
        TrafficSign(
            id = 72,
            name = "গবাদি পশু",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="32" text-anchor="middle" dominant-baseline="middle">🐄</text></svg>"""
        ),
        TrafficSign(
            id = 73,
            name = "বন্য প্রাণী",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="32" text-anchor="middle" dominant-baseline="middle">🦌</text></svg>"""
        ),
        TrafficSign(
            id = 74,
            name = "অসমতল সড়ক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M25,72 C32,58 38,58 45,72 C52,58 58,58 65,72" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 75,
            name = "নদী বা খাদের কিনার",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M20,72 L45,72 L45,82" fill="none" stroke="black" stroke-width="6"/><g transform="translate(25, 54) rotate(15) scale(0.3)"><path d="M10,30 L15,18 Q18,12 30,12 L50,12 L78,40 Z" fill="black"/></g><path d="M45,82 Q55,75 65,82 T80,82" fill="none" stroke="#1E88E5" stroke-width="4"/></svg>"""
        ),
        TrafficSign(
            id = 76,
            name = "সড়কে কাজ চলছে",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="34" text-anchor="middle" dominant-baseline="middle">🚧</text></svg>"""
        ),
        TrafficSign(
            id = 77,
            name = "অন্যান্য বিপদাশঙ্কা",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><circle cx="50" cy="54" r="14" fill="none" stroke="black" stroke-width="5"/><circle cx="50" cy="54" r="4" fill="black"/></svg>"""
        ),
        TrafficSign(
            id = 78,
            name = "নিচু উড়ন্ত বিমান",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="32" text-anchor="middle" dominant-baseline="middle">✈️</text></svg>"""
        ),
        TrafficSign(
            id = 79,
            name = "সামনে প্রতিবন্ধক",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><rect x="25" y="54" width="50" height="15" fill="black" rx="2"/></svg>"""
        ),
        TrafficSign(
            id = 80,
            name = "সরু সেতু",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M32,35 L32,48 L42,58 L42,75 M68,35 L68,48 L58,58 L58,75" fill="none" stroke="black" stroke-width="8" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 81,
            name = "ফেরিঘাট",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="32" text-anchor="middle" dominant-baseline="middle">⛴️</text></svg>"""
        ),
        TrafficSign(
            id = 82,
            name = "অরক্ষিত রেলক্রসিং",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><text x="50" y="58" font-size="32" text-anchor="middle" dominant-baseline="middle">🚂</text></svg>"""
        ),
        TrafficSign(
            id = 83,
            name = "রক্ষিত রেলক্রসিং",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M28,45 L72,45 M28,65 L72,65 M34,40 L34,70 M50,40 L50,70 M66,40 L66,70" fill="none" stroke="black" stroke-width="4" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 84,
            name = "লেভেল ক্রসিং-এর অগ্রিম সতর্কীকরণ সংকেত",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><g transform="translate(10, -5)"><line x1="30" y1="70" x2="50" y2="40" stroke="#E53935" stroke-width="8" stroke-linecap="round"/><line x1="50" y1="70" x2="30" y2="40" stroke="#E53935" stroke-width="8" stroke-linecap="round"/></g></svg>"""
        ),
        TrafficSign(
            id = 85,
            name = "তীব্র বাঁক নির্দেশক (চেভরণ)",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M40,40 L25,55 L40,70 M60,40 L45,55 L60,70" fill="none" stroke="black" stroke-width="8" stroke-linecap="round" stroke-linejoin="round"/></svg>"""
        ),
        TrafficSign(
            id = 112,
            name = "সামনে গতিরোধক (Speed Breaker)",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M30,75 L42,75 C45,75 47,62 50,62 C53,62 55,75 58,75 L70,75" fill="none" stroke="black" stroke-width="8" stroke-linecap="round" stroke-linejoin="round"/></svg>"""
        ),
        TrafficSign(
            id = 113,
            name = "ঝুঁকিপূর্ণ খাদ বা গর্ত",
            category = "warning",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><polygon points="50,12 8,88 92,88" fill="white" stroke="#E53935" stroke-width="8" stroke-linejoin="round"/><path d="M30,65 L42,65 C45,65 47,78 50,78 C53,78 55,65 58,65 L70,65" fill="none" stroke="black" stroke-width="8" stroke-linecap="round" stroke-linejoin="round"/></svg>"""
        )
    )

    val info = listOf(
        TrafficSign(
            id = 86,
            name = "সামনে চলাচলের রাস্তা নেই (অন্ধ গলি)",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><rect x="42" y="35" width="16" height="45" fill="white"/><rect x="25" y="22" width="50" height="15" fill="#E53935"/></svg>"""
        ),
        TrafficSign(
            id = 87,
            name = "পথচারী পারাপার",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><polygon points="50,15 15,80 85,80" fill="white"/><text x="50" y="60" font-size="28" text-anchor="middle" dominant-baseline="middle">🚶</text></svg>"""
        ),
        TrafficSign(
            id = 88,
            name = "গাড়ি পার্কিং করার জায়গা",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="60" font-weight="bold" fill="white" text-anchor="middle" dominant-baseline="middle">P</text></svg>"""
        ),
        TrafficSign(
            id = 89,
            name = "ফিলিং স্টেশন",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">⛽</text></svg>"""
        ),
        TrafficSign(
            id = 90,
            name = "মোটরযান মেরামত স্থল",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🔧</text></svg>"""
        ),
        TrafficSign(
            id = 91,
            name = "প্রাথমিক চিকিৎসা কেন্দ্র",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><rect x="25" y="25" width="50" height="50" rx="4" fill="white"/><rect x="44" y="32" width="12" height="36" fill="#E53935"/><rect x="32" y="44" width="36" height="12" fill="#E53935"/></svg>"""
        ),
        TrafficSign(
            id = 92,
            name = "হাসপাতাল",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🏥</text></svg>"""
        ),
        TrafficSign(
            id = 93,
            name = "রেস্তোরাঁ",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🍽️</text></svg>"""
        ),
        TrafficSign(
            id = 94,
            name = "বনভোজন এলাকা",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🌲</text></svg>"""
        ),
        TrafficSign(
            id = 95,
            name = "মসজিদ",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🕌</text></svg>"""
        ),
        TrafficSign(
            id = 96,
            name = "মন্দির",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🛕</text></svg>"""
        ),
        TrafficSign(
            id = 97,
            name = "গির্জা",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">⛪</text></svg>"""
        ),
        TrafficSign(
            id = 98,
            name = "ফায়ার সার্ভিস স্টেশন",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🚒</text></svg>"""
        ),
        TrafficSign(
            id = 99,
            name = "শৌচাগার",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🚻</text></svg>"""
        ),
        TrafficSign(
            id = 100,
            name = "সাইকেল এবং রিকশা চলাচলের লেন",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="38" font-size="28" text-anchor="middle" dominant-baseline="middle">🚲</text><g transform="translate(15, 45) scale(0.7)"><circle cx="20" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><circle cx="65" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><circle cx="85" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><path d="M20,50 L40,25 L65,50 M40,25 L85,50 M30,15 L50,15" fill="none" stroke="white" stroke-width="4"/></g></svg>"""
        ),
        TrafficSign(
            id = 101,
            name = "রিকশা চলাচলের লেন",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><g transform="translate(15, 25) scale(0.7)"><circle cx="20" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><circle cx="65" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><circle cx="85" cy="50" r="12" fill="none" stroke="white" stroke-width="3"/><path d="M20,50 L40,25 L65,50 M40,25 L85,50 M30,15 L50,15" fill="none" stroke="white" stroke-width="4"/></g></svg>"""
        ),
        TrafficSign(
            id = 102,
            name = "সাইকেল চলাচলের লেন",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🚲</text></svg>"""
        ),
        TrafficSign(
            id = 103,
            name = "বাস থামার স্থান",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🚌</text></svg>"""
        ),
        TrafficSign(
            id = 104,
            name = "ট্যাক্সি দাঁড়ানোর স্থান",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">🚕</text></svg>"""
        ),
        TrafficSign(
            id = 105,
            name = "থানা (পুলিশ স্টেশন)",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="40" font-size="16" fill="white" font-weight="bold" text-anchor="middle" dominant-baseline="middle">পুলিশ</text><text x="50" y="65" font-size="16" fill="white" font-weight="bold" text-anchor="middle" dominant-baseline="middle">POLICE</text></svg>"""
        ),
        TrafficSign(
            id = 106,
            name = "টোল সড়ক বা সেতু (পথশুল্ক)",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="40" font-size="16" fill="white" font-weight="bold" text-anchor="middle" dominant-baseline="middle">পথশুল্ক</text><text x="50" y="65" font-size="16" fill="white" font-weight="bold" text-anchor="middle" dominant-baseline="middle">TOLL</text></svg>"""
        ),
        TrafficSign(
            id = 107,
            name = "অগ্রিম পথ নির্দেশক (যশোর + নড়াইল)",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#2E7D32" stroke="white" stroke-width="2"/><text x="50" y="38" font-size="14" fill="white" font-weight="bold" text-anchor="middle">যশোর</text><text x="50" y="68" font-size="14" fill="white" font-weight="bold" text-anchor="middle">নড়াইল</text><path d="M50,44 L50,56 M44,50 L56,50" fill="none" stroke="white" stroke-width="4" stroke-linecap="round"/></svg>"""
        ),
        TrafficSign(
            id = 108,
            name = "পথ নির্দেশক ফলক (দূরত্ব সহ)",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="black" stroke="white" stroke-width="4"/><text x="50" y="38" font-size="14" fill="white" font-weight="bold" text-anchor="middle">যশোর</text><text x="50" y="68" font-size="14" fill="white" font-weight="bold" text-anchor="middle">নড়াইল</text></svg>"""
        ),
        TrafficSign(
            id = 109,
            name = "পাঠাগার",
            category = "info",
            sign = """<svg viewBox="0 0 100 100" width="100%" height="100%"><rect x="5" y="5" width="90" height="90" rx="10" fill="#1E88E5"/><text x="50" y="54" font-size="44" text-anchor="middle" dominant-baseline="middle">📖</text></svg>"""
        )
    )

    val all = mandatory + warning + info
}
