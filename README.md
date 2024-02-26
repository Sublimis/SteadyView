[![Release](https://jitpack.io/v/Sublimis/SteadyView.svg)](https://jitpack.io/#Sublimis/SteadyView)

# ⛵ SteadyView library for Android and Wear 🏝️

### Make on-screen reading easier by softening small movements of mobile screens.


Ever been in a moving vehicle trying to read?


- [Stilly screen stabilizer](https://github.com/Sublimis/SteadyScreen/) is a service that allows compatible Android and Wear apps to easily soften small device movements within their user interface.
- Use the SteadyView library to make your Android or Wear application compatible with very little effort.

## To use SteadyView library in your app

This library enables you to implement the functionality in your own custom View or ViewGroup. If you need ready-to-use "Steady…" implementations of most common Android layouts (like e.g. [LinearLayout](https://developer.android.com/reference/android/widget/LinearLayout) or [ConstraintLayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout)), you can use the [SteadyViews](https://github.com/Sublimis/SteadyViews) library instead.


1. Add the following line to your `build.gradle` file (find more info at [https://jitpack.io/#Sublimis/SteadyView](https://jitpack.io/#Sublimis/SteadyView)):

```groovy
implementation 'com.github.Sublimis:SteadyView:v1.3.1'
```
2. Let your custom [`android.view.View`](https://developer.android.com/reference/android/view/View) or [`android.view.ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup) implement the [`lib.sublimis.steadyview.ISteadyView`](https://github.com/Sublimis/SteadyView/blob/master/app/src/main/java/lib/sublimis/steadyview/ISteadyView.java) interface. This helper interface has no required methods, only a few methods that you can call from your code.
3. Call the `ISteadyView.super.initSteadyView()` in every constructor of your custom view.
4. Override the `android.view.View.performAccessibilityAction(int, Bundle)` method, from which you should call the `ISteadyView.super.performSteadyViewAction(int, Bundle)`, like so:
```java
   @Override
   public boolean performAccessibilityAction(final int action, @Nullable final Bundle arguments)
   {
      final boolean status = ISteadyView.super.performSteadyViewAction(action, arguments);

      return super.performAccessibilityAction(action, arguments) || status;
   }
```
5. Install and enable the Stilly screen stabilizer accessibility service from the [Play Store](https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen).
6. Enjoy!


## About the service

[Stilly](https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen) application uses the [AccessibilityService API](https://developer.android.com/reference/android/accessibilityservice/AccessibilityService) to retrieve interactive windows on the screen, in order to find compatible ones. The service then sends multiple "move window" accessibility actions to such windows, as needed, to perform the intended function. The data accessed during the process, using Android's AccessibilityService API, can be of personal and confidential nature (i.e. sensitive information). The application never collects, stores nor shares that data in any way.

⚡ The service has been crafted very meticulously, in order to minimize resource usage and maximize performance. It uses only the accelerometer sensor to achieve the goal.


## Example

Below is the complete implementation of the functionality in a custom view that overrides the plain [`android.view.View`](https://developer.android.com/reference/android/view/View).

If you need ready-to-use "Steady…" implementations of the most common Android layouts (like e.g. [LinearLayout](https://developer.android.com/reference/android/widget/LinearLayout) or [ConstraintLayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout)), you can use the [SteadyViews](https://github.com/Sublimis/SteadyViews) library instead.

```java
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import lib.sublimis.steadyview.ISteadyView;

public class SteadyView extends View implements ISteadyView
{
   public SteadyView(final Context context)
   {
      super(context);

      ISteadyView.super.initSteadyView();
   }

   public SteadyView(final Context context, @Nullable final AttributeSet attrs)
   {
      super(context, attrs);

      ISteadyView.super.initSteadyView();
   }

   public SteadyView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr)
   {
      super(context, attrs, defStyleAttr);

      ISteadyView.super.initSteadyView();
   }

   public SteadyView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes)
   {
      super(context, attrs, defStyleAttr, defStyleRes);

      ISteadyView.super.initSteadyView();
   }

   @Override
   public boolean performAccessibilityAction(final int action, @Nullable final Bundle arguments)
   {
      final boolean status = ISteadyView.super.performSteadyViewAction(action, arguments);

      return super.performAccessibilityAction(action, arguments) || status;
   }
}
```
