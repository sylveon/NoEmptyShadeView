package me.charlesmilette.noemptyshadeview;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.XC_MethodReplacement;

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("com.android.systemui")) {
            findAndHookMethod("com.android.systemui.statusbar.phone.NotificationPanelView", loadPackageParam.classLoader, "updateEmptyShadeView", XC_MethodReplacement.DO_NOTHING);
        }
    }
}