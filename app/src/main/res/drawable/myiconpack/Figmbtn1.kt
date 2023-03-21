package myiconpack

import MyIconPack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val MyIconPack.Figmbtn1: ImageVector
    get() {
        if (_figmbtn1 != null) {
            return _figmbtn1!!
        }
        _figmbtn1 = Builder(name = "Figmbtn1", defaultWidth = 223.0.dp, defaultHeight = 223.0.dp,
                viewportWidth = 223.0f, viewportHeight = 223.0f).apply {
            path(fill = SolidColor(Color(0xFFD6CBAF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.0f, 0.0f)
                horizontalLineToRelative(222.0f)
                verticalLineToRelative(222.0f)
                horizontalLineToRelative(-222.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFE4D089)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(25.5f, 115.5f)
                curveTo(11.9f, 97.9f, 3.167f, 112.167f, 0.5f, 121.5f)
                verticalLineTo(222.5f)
                horizontalLineTo(222.5f)
                verticalLineTo(1.0f)
                curveTo(199.333f, 0.667f, 155.4f, 0.2f, 165.0f, 1.0f)
                curveTo(177.0f, 2.0f, 213.0f, 20.0f, 192.0f, 85.5f)
                curveTo(171.0f, 151.0f, 126.5f, 159.0f, 99.5f, 157.0f)
                curveTo(72.5f, 155.0f, 42.5f, 137.5f, 25.5f, 115.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFFC1AA7E)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(121.0f, 174.5f)
                curveTo(162.6f, 173.7f, 205.667f, 145.167f, 222.0f, 131.0f)
                verticalLineTo(221.5f)
                horizontalLineTo(1.5f)
                verticalLineTo(128.0f)
                curveTo(16.5f, 110.0f, 22.5f, 131.0f, 33.0f, 139.5f)
                curveTo(43.5f, 148.0f, 69.0f, 175.5f, 121.0f, 174.5f)
                close()
            }
        }
        .build()
        return _figmbtn1!!
    }

private var _figmbtn1: ImageVector? = null
