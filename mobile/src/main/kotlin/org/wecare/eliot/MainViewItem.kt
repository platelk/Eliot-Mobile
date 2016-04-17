package org.wecare.eliot

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife

/**
 * Action available in the main menu
 */
class MainViewItem(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {
    /**
     * Title present by the view
     */
    var titleText: String = ""

    /**
     * Resource id used for the icon
     */
    var resIcon: Int = 0

    /**
     * Background color for the circle icon
     */
    var backgroundIconColor: Int = 0

    /**
     * Icon
     */
    @Bind(R.id.iconImage) lateinit var iconImage: ImageView

    /**
     * Title
     */
    @Bind(R.id.titleText) lateinit var titleTextView: TextView

    init {
        if (!isInEditMode) {
            val attr = context?.theme?.obtainStyledAttributes(attrs, R.styleable.MainViewItem, 0, 0)
            try {
                this.titleText = attr?.getString(R.styleable.MainViewItem_mainViewTitle)!!
                this.resIcon = attr?.getResourceId(R.styleable.MainViewItem_mainViewIcon, R.drawable.face_mustache_eliot)!!
            } finally {
                attr?.recycle()
            }
            initialize(context)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context?) : this(context, null) {
    }

    /**
     * Present the data in the view
     *
     * @param title title to display
     * @param resIcon id of the icon to draw in the item
     */
    fun presentData(title: String, resIcon: Int, backgroundColor: Int = 0) {
        this.titleText = title
        this.resIcon = resIcon

        this.titleTextView.text = title
        this.iconImage.setImageDrawable(resources.getDrawable(resIcon))
        this.iconImage.backgroundTintList = ColorStateList.valueOf(if (backgroundColor == 0) resources.getColor(backgroundColor) else backgroundColor)
    }

    /**
     * Initialize the view
     */
    fun initialize(context : Context?) {
        inflate(context, R.layout.view_main_item, this)
        ButterKnife.bind(this)
        this.iconImage.background = resources.getDrawable(R.drawable.ic_circle_black);
    }
}