package myiconpack.repoaneckdots.git

import androidx.compose.ui.graphics.vector.ImageVector
import myiconpack.repoaneckdots.git.refs.AllIcons
import myiconpack.repoaneckdots.git.refs.Heads
import myiconpack.repoaneckdots.git.refs.Tags
import myiconpack.repoaneckdots.gitGroup
import kotlin.collections.List as ____KtList

public object RefsGroup

public val gitGroup.Refs: RefsGroup
  get() = RefsGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val RefsGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= Heads.AllIcons + Tags.AllIcons + listOf()
    return __AllIcons!!
  }
