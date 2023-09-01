package ru.plumsoftware.uicomponents.plumsoftwareiconpack

import androidx.compose.ui.graphics.vector.ImageVector
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.settings.Darkmode
import kotlin.collections.List as ____KtList

public object SettingsGroup

public val PlumsoftwareIconPack.Settings: SettingsGroup
  get() = SettingsGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val SettingsGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Darkmode)
    return __AllIcons!!
  }
