package com.nekzabirov.firebaseapp.ex

import platform.Foundation.NSError

fun NSError?.toKotlin() = Exception(this?.localizedDescription ?: "unknown")