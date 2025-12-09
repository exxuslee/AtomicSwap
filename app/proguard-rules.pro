-dontwarn kotlinx.serialization.**
-dontwarn io.ktor.**
-dontwarn org.koin.**

-keepattributes *Annotation*

-keep class com.sun.jna.** { *; }
-keepclassmembers class com.sun.jna.** {
    native <methods>;
    *;
}

-keep class uniffi.** { *; }

# Preserve all public and protected fields and methods
-keepclassmembers class ** {
    public *;
    protected *;
}

-dontwarn uniffi.**
-dontwarn com.sun.jna.**

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn java.beans.ConstructorProperties
-dontwarn java.beans.Transient