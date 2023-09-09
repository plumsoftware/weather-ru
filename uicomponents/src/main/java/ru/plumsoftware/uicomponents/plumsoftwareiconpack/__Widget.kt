package ru.plumsoftware.uicomponents.plumsoftwareiconpack

import androidx.compose.ui.graphics.vector.ImageVector
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.widget.WidgetBack
import kotlin.collections.List as ____KtList

public object WidgetGroup

public val PlumsoftwareIconPack.Widget: WidgetGroup
  get() = WidgetGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val WidgetGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(WidgetBack)
    return __AllIcons!!
  }
