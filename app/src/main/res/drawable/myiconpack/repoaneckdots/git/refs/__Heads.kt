package myiconpack.repoaneckdots.git.refs

import androidx.compose.ui.graphics.vector.ImageVector
import myiconpack.repoaneckdots.git.RefsGroup
import kotlin.collections.List as ____KtList

public object HeadsGroup

public val RefsGroup.Heads: HeadsGroup
  get() = HeadsGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val HeadsGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf()
    return __AllIcons!!
  }
