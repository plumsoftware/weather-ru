package ru.plumsoftware.uicomponents

import androidx.compose.ui.graphics.vector.ImageVector
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.AllIcons
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Settings
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Widget
import kotlin.collections.List as ____KtList

public object PlumsoftwareIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val PlumsoftwareIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= Settings.AllIcons + Weather.AllIcons + Widget.AllIcons + listOf()
    return __AllIcons!!
  }
