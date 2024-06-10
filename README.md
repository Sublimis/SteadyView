[![Release](https://jitpack.io/v/Sublimis/SteadyView.svg)](https://jitpack.io/#Sublimis/SteadyView)

# ⛵ SteadyView library for Android and Wear 🏝️


## More info

Please see the [SteadyScreen](https://github.com/Sublimis/SteadyScreen) project for more details.


## Project components

- [SteadyScreen service app](https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen): The engine behind the scenes.
- [SteadyViews library](https://github.com/Sublimis/SteadyViews): Ready-to-use "Steady…" implementations of most common Android layouts (like e.g. LinearLayout or ConstraintLayout).
- SteadyView library (this): Core classes and methods. To be used for custom View or ViewGroup implementations.
- [SteadyService library](https://github.com/Sublimis/SteadyService): Details of the service implementation.


## To use this library in your app

This library enables you to implement the functionality in your own custom View or ViewGroup. If you need ready-to-use "Steady…" implementations of most common Android layouts (like e.g. [LinearLayout](https://developer.android.com/reference/android/widget/LinearLayout) or [ConstraintLayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout)), you can use the [SteadyViews](https://github.com/Sublimis/SteadyViews) library instead.


1. Add the following line to your `build.gradle` file (find more info at [https://jitpack.io/#Sublimis/SteadyView](https://jitpack.io/#Sublimis/SteadyView)):

```groovy
implementation 'com.github.Sublimis:SteadyView:1.3.1'
```
2. Let your custom [`android.view.View`](https://developer.android.com/reference/android/view/View) or [`android.view.ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup) implement the [`lib.sublimis.steadyview.ISteadyView`](https://github.com/Sublimis/SteadyView/blob/master/app/src/main/java/lib/sublimis/steadyview/ISteadyView.java) helper interface. Call the `ISteadyView.super.initSteadyView()` from every constructor of your custom view.
```java
   public class MyView extends View implements ISteadyView
   {
      public MyView(final Context context)
      {
         super(context);
   
         ISteadyView.super.initSteadyView();
      }

      ...
   }
```

3. Override the `android.view.View.performAccessibilityAction(int, Bundle)` method, from which you should call the `ISteadyView.super.performSteadyViewAction(int, Bundle)`, like so:
```java
   @Override
   public boolean performAccessibilityAction(final int action, @Nullable final Bundle arguments)
   {
      final boolean status = ISteadyView.super.performSteadyViewAction(action, arguments);

      return super.performAccessibilityAction(action, arguments) || status;
   }
```

4. Install and enable the SteadyScreen accessibility service from the [Play Store](https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen).

5. Enjoy!


## Enable or disable programatically

Call the `ISteadyView.setSteadyViewEnabled(final boolean enabled)` method on your ISteadyView to disable or (re)enable the functionality.

Disable:
```java
myView.setSteadyViewEnabled(false);
```

Enable:
```java
myView.setSteadyViewEnabled(true);
```

Note, this does not disable/enable the service, it just tells the View to ignore all service inputs.
Call the `boolean ISteadyView.isSteadyViewEnabled()` on your ISteadyView to check the enabled state.


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
