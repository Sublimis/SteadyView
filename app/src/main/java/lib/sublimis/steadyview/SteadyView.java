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

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import lib.sublimis.steadyview.HSteadyView.SteadyAccDelegate;

/**
 * Interface to the <a href="https://play.google.com/store/apps/details?id=com.sublimis.steadyscreen">SteadyScreen</a> service
 * which allows compatible applications to easily counteract small screen movements within their GUI.
 * <p>
 * Find out more at <a href="https://github.com/Sublimis/SteadyView/">https://github.com/Sublimis/SteadyView/</a>.
 *
 * @author Sublimis
 * @version 2.0 (2024-11)
 */
public class SteadyView extends View implements ISteadyView
{
	protected volatile SteadyAccDelegate mSteadyAccDelegate = null;

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

	@Override
	public SteadyAccDelegate getSteadyAccDelegate()
	{
		return mSteadyAccDelegate;
	}

	@Override
	public void setSteadyAccDelegate(SteadyAccDelegate steadyAccDelegate)
	{
		mSteadyAccDelegate = steadyAccDelegate;
	}
}
