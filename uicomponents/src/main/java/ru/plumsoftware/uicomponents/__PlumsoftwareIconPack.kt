package ru.plumsoftware.uicomponents

import androidx.compose.ui.graphics.vector.ImageVector
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.PlumsoftwareIcons
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Settings
import kotlin.collections.List as ____KtList

public object PlumsoftwareIconPack

private var __PlumsoftwareIcons: ____KtList<ImageVector>? = null

public val PlumsoftwareIconPack.PlumsoftwareIcons: ____KtList<ImageVector>
  get() {
    if (__PlumsoftwareIcons != null) {
      return __PlumsoftwareIcons!!
    }
    __PlumsoftwareIcons= Settings.PlumsoftwareIcons + listOf()
    return __PlumsoftwareIcons!!
  }
