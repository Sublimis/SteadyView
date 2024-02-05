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

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.Nullable;

/**
 * Interface to the <a href="https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen">SteadyScreen</a> service
 * which allows compatible applications to easily counteract small screen movements within their GUI.
 * <p>
 * Find out more at <a href="https://github.com/Sublimis/SteadyView/">https://github.com/Sublimis/SteadyView/</a>.
 *
 * @author Sublimis
 * @version 1.0 (2024-02)
 */
public interface ISteadyView
{
	AccessibilityAction STEADY_ACTION = VERSION.SDK_INT >= VERSION_CODES.O ? new AccessibilityAction(AccessibilityAction.ACTION_MOVE_WINDOW.getId(), "(SteadyView)") : null;
	String ARG_ID = "lib.steadyview.ID";
	String ARG_MOVE_X = "lib.steadyview.MOVE_X";
	String ARG_MOVE_Y = "lib.steadyview.MOVE_Y";
	AtomicBoolean mIsEnabled = new AtomicBoolean(true);

	/**
	 * Get the SteadyView feature global enabled state.
	 *
	 * @return true if SteadyView feature is enabled globally
	 */
	default boolean isSteadyViewEnabled()
	{
		return mIsEnabled.get();
	}

	/**
	 * Set the SteadyView feature global enabled state.
	 *
	 * @param enabled true to enable the SteadyView feature globally, false to disable
	 * @return true if SteadyView feature enabled state was changed as a result of this call
	 */
	default boolean setSteadyViewEnabled(boolean enabled)
	{
		final boolean retVal = mIsEnabled.compareAndSet(!enabled, enabled);

		if (false == enabled && retVal)
		{
			undoSteadyViewAction();
		}

		return retVal;
	}

	/**
	 * Init the SteadyView feature. This should be called from the custom view constructor.
	 */
	default void initSteadyView()
	{
		HSteadyView.initSteadyView(this);
	}

	/**
	 * Perform the SteadyView feature accessibility action if requested.
	 *
	 * @param action The accessibility action to perform.
	 * @param arguments Optional accessibility action arguments.
	 * @return true if the SteadyView action was performed, false if SteadyView feature is disabled or the action was not recognized.
	 */
	default boolean performSteadyViewAction(final int action, @Nullable final Bundle arguments)
	{
		return HSteadyView.performSteadyViewAction(this, action, arguments);
	}

	/**
	 * Undo all side effects of previous SteadyView feature actions.
	 */
	default void undoSteadyViewAction()
	{
		HSteadyView.undoSteadyViewAction(this);
	}
}
