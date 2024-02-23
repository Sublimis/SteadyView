/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lib.sublimis.steadyview;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Interface to the <a href="https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen">SteadyScreen</a> service
 * which allows compatible applications to easily counteract small screen movements within their GUI.
 * <p>
 * Find out more at <a href="https://github.com/Sublimis/SteadyView/">https://github.com/Sublimis/SteadyView/</a>.
 *
 * @author Sublimis
 * @version 1.3 (2024-02)
 */
@SuppressLint("ObsoleteSdkInt")
public class HSteadyView
{
	protected static final long UndoTimeout = TimeUnit.SECONDS.toNanos(2);
	protected static final long UndoCheckTimeout = TimeUnit.MILLISECONDS.toNanos(1000);
	protected static long mActionTime = 0, mUndoCheckTime = 0;

	public static void initSteadyView(@NonNull final ISteadyView steadyView)
	{
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP && steadyView instanceof View)
		{
			((View) steadyView).setAccessibilityDelegate(new AccessibilityDelegate()
			{
				@Override
				public void onInitializeAccessibilityNodeInfo(@NonNull final View host, @NonNull final AccessibilityNodeInfo info)
				{
					super.onInitializeAccessibilityNodeInfo(host, info);

					info.addAction(ISteadyView.STEADY_ACTION);
					info.setText(ISteadyView.STEADY_ACTION.getLabel());
				}
			});

			((View) steadyView).setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
		}
	}

	public static boolean performSteadyViewAction(@NonNull final ISteadyView steadyView, final int action, @Nullable final Bundle arguments)
	{
		boolean retVal = false;

		if (steadyView.isSteadyViewEnabled() && steadyView instanceof View)
		{
			if (arguments != null && isSteadyViewAction(action, arguments))
			{
				final int x = arguments.getInt(ISteadyView.ARG_MOVE_X, Integer.MIN_VALUE);
				final int y = arguments.getInt(ISteadyView.ARG_MOVE_Y, Integer.MIN_VALUE);

				if (x != Integer.MIN_VALUE)
				{
					((View) steadyView).setTranslationX(x);

					retVal = true;
				}

				if (y != Integer.MIN_VALUE)
				{
					((View) steadyView).setTranslationY(y);

					retVal = true;
				}

				manageUndo(steadyView);
			}
		}

		return retVal;
	}

	public static void undoSteadyViewAction(@NonNull final ISteadyView steadyView)
	{
		if (steadyView instanceof View)
		{
			((View) steadyView).setTranslationX(0);
			((View) steadyView).setTranslationY(0);
		}
	}

	protected static void manageUndo(@NonNull final ISteadyView steadyView)
	{
		final long timestamp = SystemClock.elapsedRealtimeNanos();

		if (isUndoCheckNeeded(timestamp))
		{
			((View) steadyView).postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					if (isUndoNeeded())
					{
						undoSteadyViewAction(steadyView);
					}
				}
			}, TimeUnit.NANOSECONDS.toMillis(UndoTimeout + UndoCheckTimeout) + 500);

			mUndoCheckTime = timestamp;
		}

		mActionTime = timestamp;
	}

	protected static boolean isUndoNeeded()
	{
		final long timestamp = SystemClock.elapsedRealtimeNanos();

		return timestamp - mActionTime > UndoTimeout;
	}

	protected static boolean isUndoCheckNeeded(final long timestamp)
	{
		return timestamp - mUndoCheckTime > UndoCheckTimeout;
	}

	public static boolean isSteadyViewAction(final int action, @Nullable final Bundle arguments)
	{
		boolean retVal = false;

		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP)
		{
			if (action == ISteadyView.STEADY_ACTION.getId())
			{
				retVal = true;
			}
		}

		return retVal;
	}

	public static void populateSteadyViewArguments(final Bundle arguments, final int x, final int y)
	{
		if (arguments != null)
		{
			arguments.putInt(ISteadyView.ARG_MOVE_X, x);
			arguments.putInt(ISteadyView.ARG_MOVE_Y, y);
		}
	}
}
