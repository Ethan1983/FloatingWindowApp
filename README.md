# FloatingWindowApp
shouldShowRequestPermissionRationale fails for SYSTEM_ALERT_WINDOW

ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW) never returns true even
if the user had denied access in previous attempts.
