# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Flash/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontobfuscate
#-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

-dontwarn com.google.common.**
-dontwarn com.android.volley.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn com.wdullaer.**
-dontwarn org.apache.**
-dontwarn com.squareup.okhttp.**
-dontwarn android.support.**
-keepattributes InnerClasses, EnclosingMethod
-keepattributes Signature
-keepattributes Exceptions
-keepattributes SourceFile, LineNumberTable

-keep class retrofit2.** { *; }

# ButterKnife proguard rules, we will remove this when andoird binding fully added in our project
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


