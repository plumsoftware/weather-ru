package ru.plumsoftware.uicomponents.plumsoftwareiconpack

import androidx.compose.ui.graphics.vector.ImageVector
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.settings.Darkmode
import kotlin.collections.List as ____KtList

public object SettingsGroup

public val PlumsoftwareIconPack.Settings: SettingsGroup
  get() = SettingsGroup

private var __PlumsoftwareIcons: ____KtList<ImageVector>? = null

public val SettingsGroup.PlumsoftwareIcons: ____KtList<ImageVector>
  get() {
    if (__PlumsoftwareIcons != null) {
      return __PlumsoftwareIcons!!
    }
    __PlumsoftwareIcons= listOf(Darkmode)
    return __PlumsoftwareIcons!!
  }
