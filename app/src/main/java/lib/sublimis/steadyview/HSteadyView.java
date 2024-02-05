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
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Interface to the <a href="https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen">SteadyScreen</a> service
 * which allows compatible applications to easily counteract small screen movements within their GUI.
 * <p>
 * Find out more at <a href="https://github.com/Sublimis/SteadyView/">https://github.com/Sublimis/SteadyView/</a>.
 *
 * @author Sublimis
 * @version 1.2 (2024-02)
 */
@SuppressLint("ObsoleteSdkInt")
public class HSteadyView
{
	public static void initSteadyView(@NonNull final ISteadyView steadyView)
	{
		if (VERSION.SDK_INT >= VERSION_CODES.O && steadyView instanceof View)
		{
			((View) steadyView).setAccessibilityDelegate(new AccessibilityDelegate()
			{
				@Override
				public void onInitializeAccessibilityNodeInfo(@NonNull final View host, @NonNull final AccessibilityNodeInfo info)
				{
					super.onInitializeAccessibilityNodeInfo(host, info);

					info.addAction(ISteadyView.STEADY_ACTION);
				}
			});
		}
	}

	public static boolean performSteadyViewAction(@NonNull final ISteadyView steadyView, final int action, @Nullable final Bundle arguments)
	{
		boolean retVal = false;

		if (arguments != null && steadyView instanceof View && steadyView.isSteadyViewEnabled())
		{
			if (isSteadyViewAction(action, arguments))
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

	public static boolean isSteadyViewAction(final int action, @Nullable final Bundle arguments)
	{
		boolean retVal = false;

		if (VERSION.SDK_INT >= VERSION_CODES.O)
		{
			if (arguments != null && action == ISteadyView.STEADY_ACTION.getId())
			{
				retVal = arguments.containsKey(ISteadyView.ARG_ID);
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
			arguments.putString(ISteadyView.ARG_ID, ISteadyView.ARG_ID);
		}
	}
}
