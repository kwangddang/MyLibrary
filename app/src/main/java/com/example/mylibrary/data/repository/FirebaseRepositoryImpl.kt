package com.example.mylibrary.data.repository

import com.example.mylibrary.common.KeyName.BOOK
import com.example.mylibrary.common.KeyName.BOOKMARK
import com.example.mylibrary.common.KeyName.BOOKMARKED_BY
import com.example.mylibrary.common.KeyName.BOOKMARK_COUNT
import com.example.mylibrary.common.KeyName.CATEGORY
import com.example.mylibrary.common.KeyName.RATED_BY
import com.example.mylibrary.common.KeyName.RATING
import com.example.mylibrary.common.KeyName.RATING_AVERAGE
import com.example.mylibrary.common.KeyName.USERNAME
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.entity.firebase.User
import com.example.mylibrary.data.entity.room.Book
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @Named("UserDB") private val firebaseUserDB: DatabaseReference,
    @Named("BookDB") private val firebaseBookDB: DatabaseReference
) : FirebaseRepository {

    private val uid = getUserAuth()?.uid.orEmpty()

    override fun getUserAuth(): FirebaseUser? = firebaseAuth.currentUser

    override fun setBookmark(bookInfo: BookInfo) {
        val bookDB = firebaseBookDB.child(bookInfo.isbn)
        val bookmarkDB = bookDB.child(BOOKMARK)
        val bookmarkCountDB = bookmarkDB.child(BOOKMARK_COUNT)
        val bookmarkedByDB = bookmarkDB.child(BOOKMARKED_BY)
        val bookmarkedBy = HashMap<String, Any>()
        bookmarkedBy[uid] = uid

        firebaseBookDB.get().addOnSuccessListener {
            if(it.hasChild(bookInfo.isbn)){
                bookmarkedByDB.updateChildren(bookmarkedBy).addOnSuccessListener {
                    bookmarkedByDB.get().addOnSuccessListener { dataSnapShot ->
                        bookmarkCountDB.setValue(dataSnapShot.childrenCount)
                    }
                }
            } else{
                bookDB.setValue(bookInfo).addOnSuccessListener {
                    bookmarkedByDB.updateChildren(bookmarkedBy).addOnSuccessListener {
                        bookmarkedByDB.get().addOnSuccessListener { dataSnapShot ->
                            bookmarkCountDB.setValue(dataSnapShot.childrenCount)
                        }
                    }
                }
            }
        }
        val newBook = HashMap<String,Any>()
        newBook[bookInfo.isbn] = bookInfo
        firebaseUserDB.child(uid).child(BOOK).updateChildren(newBook)
    }

    override fun deleteBookmark(isbn: String) {
        val bookmarkDB = firebaseBookDB.child(isbn).child(BOOKMARK)
        val bookmarkCountDB = bookmarkDB.child(BOOKMARK_COUNT)
        val bookmarkedByDB = bookmarkDB.child(BOOKMARKED_BY)
        bookmarkedByDB.child(uid).removeValue().addOnSuccessListener {
            bookmarkedByDB.get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.childrenCount.toInt() == 0)
                    firebaseBookDB.child(isbn).removeValue()
                else
                    bookmarkCountDB.setValue(dataSnapshot.childrenCount)
            }
        }

        firebaseUserDB.child(uid).child(BOOK).child(isbn).removeValue()

        firebaseUserDB.child(uid).child(CATEGORY).get().addOnSuccessListener { category ->
            category.children.forEach { book ->
                if(book.hasChild(isbn)){
                    firebaseUserDB.child(uid).child(CATEGORY).child(book.key!!).removeValue()
                }
            }
        }
    }

    override fun getBookmarkCount(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(BOOKMARK).child(BOOKMARK_COUNT).get()

    override fun getRatingAverage(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(RATING).child(RATING_AVERAGE).get()

    override fun setRating(isbn: String, num: Int) {
        val ratingDB = firebaseBookDB.child(isbn).child(RATING)
        val ratedByDB = ratingDB.child(RATED_BY)
        val ratingAverageDB = ratingDB.child(RATING_AVERAGE)
        val ratedBy = HashMap<String, Any>()
        ratedBy[uid] = num

        ratedByDB.updateChildren(ratedBy).addOnSuccessListener {
            ratedByDB.get().addOnSuccessListener { dataSnapshot ->
                var sum: Double = 0.0
                dataSnapshot.children.forEach {
                    sum += it.value.toString().toDouble()
                }
                ratingAverageDB.setValue(sum / dataSnapshot.childrenCount)
            }
        }
    }

    override fun getBookmarked(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(BOOKMARK).child(BOOKMARKED_BY).get()

    override fun getAllBook(): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(BOOK).get()


    override fun setUserBook(book: BookInfo) {
        firebaseUserDB.child(uid).child(CATEGORY).child(book.isbn).setValue(book)
    }

    override fun getCategoryBook(category: String): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(CATEGORY).child(category).get()

    override fun signUp(email: String, password: String, username: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun signInWithFacebook(credential: AuthCredential): Task<AuthResult> =
        firebaseAuth.signInWithCredential(credential)

    override fun signInWithEmail(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)


    override fun getUsername(): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(USERNAME).get()

    override fun getUser(): Task<DataSnapshot> =
        firebaseUserDB.child(uid).get()

    override fun setUser(email: String, username: String): Task<Void> =
        firebaseUserDB.child(getUserAuth()?.uid.orEmpty()).setValue(User(email, username))

    override fun setUsername(username: String): Task<Void> {
        val db = firebaseUserDB.child(uid)
        val data = mutableMapOf<String, Any>()
        data["username"] = username

        return db.updateChildren(data)
    }

    override fun deleteUser(): Task<Void> =
        firebaseUserDB.child(uid).removeValue()

    override fun checkCategory(category: String): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(CATEGORY).get()

    override fun setCategory(category: String): Task<Void> {
        val temp = HashMap<String,Any>()
        temp[category] = 0
        return firebaseUserDB.child(uid).child(CATEGORY).updateChildren(temp)
    }

    override fun deleteCategory(category: String): Task<Void> =
        firebaseUserDB.child(uid).child(CATEGORY).child(category).removeValue()

    override fun getCategory(): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(CATEGORY).get()

    override fun setBookCategory(category: String, book: Book) {
        val db = firebaseUserDB.child(uid).child(CATEGORY).child(category)
        db.get().addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.childrenCount.toInt() == 1){
                if(dataSnapshot.value == 0){
                    db.setValue(book)
                } else{
                    val newBook = hashMapOf<String,Any>()
                    newBook[book.isbn] = book
                    db.updateChildren(newBook)
                }
            } else{
                val newBook = hashMapOf<String,Any>()
                newBook[book.isbn] = book
                db.updateChildren(newBook)
            }
        }
    }
}