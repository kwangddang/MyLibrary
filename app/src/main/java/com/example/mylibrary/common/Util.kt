package com.example.mylibrary.common

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.view.login.signup.SignupFragment
import com.example.mylibrary.view.root.RootFragment

fun bookInfoToBook(bookInfo: BookInfo): Book =
    Book(bookInfo.author,
        bookInfo.description,
        bookInfo.discount,
        bookInfo.image,
        bookInfo.isbn,
        bookInfo.link,
        bookInfo.price,
        bookInfo.publisher,
        bookInfo.title,
        bookInfo.bookmark,
    )
fun bookToBookInfo(book: Book): BookInfo =
    BookInfo(
        book.author,
        book.description,
        book.discount,
        book.image,
        book.isbn,
        book.link,
        book.price,
        book.publisher,
        book.title,
        book.isBookMark
    )

fun Activity.hideKeyboard(editText: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun Context.getPxFromDp(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun Fragment.showToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Activity.setTransparentStatusBar(){
    window.apply {
        setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
    if(Build.VERSION.SDK_INT >= 30) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}

fun View.setTransparentStatusBar(context: Context) = context.run {
    setPadding(0,this.statusBarHeight(),0,this.navigationHeight())
}

fun Activity.setStatusBarOrigin() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}

fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Context.navigationHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun showToast(context: Context, text: String){
    Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
}

fun Fragment.rootFrom1Depth(): RootFragment = (parentFragment as RootFragment)

fun Fragment.rootFrom2Depth(): RootFragment = (parentFragment?.parentFragment as RootFragment)

fun Fragment.signupFrom2Depth(): SignupFragment = (parentFragment?.parentFragment as SignupFragment)

fun Fragment.getColor(colorId: Int): Int = requireContext().getColor(colorId)

