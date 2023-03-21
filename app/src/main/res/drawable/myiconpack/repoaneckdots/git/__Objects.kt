package myiconpack.repoaneckdots.git

import androidx.compose.ui.graphics.vector.ImageVector
import myiconpack.repoaneckdots.git.objects.AllIcons
import myiconpack.repoaneckdots.git.objects.Info
import myiconpack.repoaneckdots.git.objects.Pack
import myiconpack.repoaneckdots.gitGroup
import kotlin.collections.List as ____KtList

public object ObjectsGroup

public val gitGroup.Objects: ObjectsGroup
  get() = ObjectsGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val ObjectsGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= Info.AllIcons + Pack.AllIcons + listOf()
    return __AllIcons!!
  }
