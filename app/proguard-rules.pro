# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android\sdk/tools/proguard/proguard-android.txt
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
-keep public class me.leolin.shortcutbadger.**
-keepclassmembers public class me.leolin.shortcutbadger.** {
    *;
}

-keep public class scalaxy.streams.**
-keepclassmembers public class scalaxy.streams.** {
    *;
}

-dontwarn scalaxy.streams.**

-dontwarn android.support.v7.appcompat.**
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote android.support.v7.appcompat.**

-dontwarn **$$anonfun$*
-dontwarn scala.beans.ScalaBeanInfo
-dontwarn scala.collection.generic.GenTraversableFactory
-dontwarn scala.collection.immutable.RedBlack$Empty
-dontwarn scala.concurrent.forkjoin.**
-dontwarn scala.reflect.**
-dontwarn scala.sys.process.**
-dontwarn scala.swing.**
-dontwarn scala.concurrent.**
-dontwarn scala.tools.**,plugintemplate.**
-dontwarn com.bumptech.glide.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.**

-dontnote org.xml.sax.EntityResolver

##temporare
-dontwarn com.kos.fastuimodule.good.common.system.post.**

# fixes an issue with async/await (https://issues.scala-lang.org/browse/SI-5397)
-keep class scala.collection.SeqLike {
    public protected *;
}

## Square Otto specific rules ##
## https://square.github.io/otto/ ##

-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

#(org.apache.james.mime4j.storage.StorageProvider)Class.forName(variable).newInstance()
-dontnote org.apache.james.mime4j.storage.DefaultStorageProvider


## Fixes ==> Warning: ... can't find referenced class sun.misc.Unsafe
-dontwarn sun.misc.Unsafe

-dontobfuscate
-dontoptimize
-dontpreverify

-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

#scala.Enumeration accesses a field 'MODULE$' dynamically
-dontnote scala.Enumeration

#(org.xml.sax.EntityResolver)Class.forName(variable).newInstance()
-dontnote org.xml.sax.EntityResolver

#(org.apache.james.mime4j.storage.StorageProvider)Class.forName(variable).newInstance()
-dontnote org.apache.james.mime4j.storage.DefaultStorageProvider

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod

# these should hopefully make any specific changes for a new project no longer needed so you can just drop in the proguard file
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

## don't let proguard screw around with actionbar sherlock (else you'll get vague errors and blame scala probably)
-keepclassmembers class * extends com.actionbarsherlock.ActionBarSherlock {
    <init>(android.app.Activity, int);
}