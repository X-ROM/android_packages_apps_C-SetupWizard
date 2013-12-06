/*
 *  Copyright (C) 2013 The ThinkingBridge Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.thinkingbridge.tbsetupwizard.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.inputmethod.InputMethodInfo;

public class InputMethodItem {
    private String imLabel;
    private String imPackage;

    public InputMethodItem(Context context, InputMethodInfo info) {
        PackageManager pm = context.getPackageManager();
        imLabel = info.loadLabel(pm).toString();
        imPackage = getRealName(info);
    }

    private String getRealName(InputMethodInfo info) {
        String packageName = info.getPackageName();
        return packageName + "/" + info.getServiceName().replace(packageName, "");
    }

    public String getImLabel() {
        return imLabel;
    }

    public String getImPackage() {
        return imPackage;
    }

}
