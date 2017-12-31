package me.charlesmilette.noemptyshadeview;

import android.os.Build;

import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class Main implements IXposedHookLoadPackage, IXposedHookInitPackageResources {
    private static final String SystemUI = "com.android.systemui";

    private static boolean isNougatOrBetter() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals(SystemUI) && !isNougatOrBetter()) {
            // N solution probably works with MM and LL too, but I have no device to check, so
            // just use a solution that I know works on those.
            XposedHelpers.findAndHookMethod(SystemUI + ".statusbar.phone.NotificationPanelView", loadPackageParam.classLoader, "updateEmptyShadeView", XC_MethodReplacement.DO_NOTHING);
        }
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        if (initPackageResourcesParam.packageName.equals(SystemUI) && isNougatOrBetter()) {
            initPackageResourcesParam.res.setReplacement(SystemUI, "string", "empty_shade_text", "");
        }
    }
}