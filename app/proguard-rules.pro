# Retrofit
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Coil
-keep class coil.** { *; }

# RevenueCat
-keep class com.revenuecat.** { *; }
