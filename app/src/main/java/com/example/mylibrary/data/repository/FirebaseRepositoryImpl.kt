package com.example.mylibrary.data.repository

import com.example.mylibrary.util.KeyName.BOOK
import com.example.mylibrary.util.KeyName.BOOKMARK
import com.example.mylibrary.util.KeyName.BOOKMARKED_BY
import com.example.mylibrary.util.KeyName.BOOKMARK_COUNT
import com.example.mylibrary.util.KeyName.CATEGORY
import com.example.mylibrary.util.KeyName.CONTENT
import com.example.mylibrary.util.KeyName.EMAIL
import com.example.mylibrary.util.KeyName.RATED_BY
import com.example.mylibrary.util.KeyName.RATING
import com.example.mylibrary.util.KeyName.RATING_AVERAGE
import com.example.mylibrary.util.KeyName.REVIEW
import com.example.mylibrary.util.KeyName.REVIEW_COUNT
import com.example.mylibrary.util.KeyName.REVIEW_ID
import com.example.mylibrary.util.KeyName.USERNAME
import com.example.mylibrary.util.KeyName.USER_ID
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.firebase.User
import com.example.mylibrary.data.entity.room.Book
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import javax.inject.Inject
import javax.inject.Named

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @Named("UserDB") private val firebaseUserDB: DatabaseReference,
    @Named("BookDB") private val firebaseBookDB: DatabaseReference
) : FirebaseRepository {
    private val uid = getUserAuth()?.uid.orEmpty()

    override fun getUserAuth(): FirebaseUser? = firebaseAuth.currentUser

    override fun signUp(email: String, password: String, username: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun signInWithFacebook(credential: AuthCredential): Task<AuthResult> =
        firebaseAuth.signInWithCredential(credential)

    override fun signInWithEmail(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)



    override fun getUsername(): Task<DataSnapshot> =
        firebaseUserDB.child(getUserAuth()?.uid.orEmpty()).child(USERNAME).get()

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



    override fun getAllBook(): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(BOOK).get()

    override fun getCategoryBook(category: String): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(CATEGORY).child(category).get()

    override fun getPopularBook(): Query =
        firebaseBookDB.orderByChild(BOOKMARK_COUNT).limitToLast(9)



    override fun checkCategory(category: String): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(CATEGORY).get()

    override fun getCategory(): Task<DataSnapshot> =
        firebaseUserDB.child(uid).child(CATEGORY).get()

    override fun setCategory(category: String): Task<Void> {
        val data = HashMap<String,Any>()
        data[category] = 0
        return firebaseUserDB.child(uid).child(CATEGORY).updateChildren(data)
    }

    override fun setBookCategory(category: String, book: Book) {
        val db = firebaseUserDB.child(uid).child(CATEGORY).child(category)
        db.get().addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.childrenCount.toInt() == 1){
                if(dataSnapshot.value == 0){
                    db.setValue(book)
                } else{
                    val data = hashMapOf<String,Any>()
                    data[book.isbn] = book
                    db.updateChildren(data)
                }
            } else{
                val data = hashMapOf<String,Any>()
                data[book.isbn] = book
                db.updateChildren(data)
            }
        }
    }

    override fun deleteCategory(category: String): Task<Void> =
        firebaseUserDB.child(uid).child(CATEGORY).child(category).removeValue()



    override fun getBookmarked(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(BOOKMARKED_BY).get()

    override fun getBookmarkCount(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(BOOKMARK_COUNT).get()

    override fun setBookmark(bookInfo: BookInfo) {
        val bookDB = firebaseBookDB.child(bookInfo.isbn)
        val bookmarkCountDB = bookDB.child(BOOKMARK_COUNT)
        val bookmarkedByDB = bookDB.child(BOOKMARKED_BY)
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
        val bookDB = firebaseBookDB.child(isbn)
        val bookmarkDB = bookDB.child(BOOKMARK)
        val bookmarkCountDB = bookmarkDB.child(BOOKMARK_COUNT)
        val bookmarkedByDB = bookmarkDB.child(BOOKMARKED_BY)

        bookmarkedByDB.child(uid).removeValue().addOnSuccessListener {
            bookmarkedByDB.get().addOnSuccessListener { dataSnapshot ->
                bookmarkCountDB.setValue(dataSnapshot.childrenCount)
            }
        }

        firebaseUserDB.child(uid).child(BOOK).child(isbn).removeValue()

        val categoryDB = firebaseUserDB.child(uid).child(CATEGORY)
        categoryDB.get().addOnSuccessListener { category ->
            category.children.forEach { book ->
                if(book.hasChild(isbn)){
                    if(book.childrenCount.toInt() == 1){
                        categoryDB.child(book.key!!).setValue(0)
                    } else{
                        categoryDB.child(book.key!!).child(isbn).removeValue()
                    }
                }
            }
        }
    }



    override fun getReview(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(REVIEW).child(REVIEW_ID).get()

    override fun getReviewCount(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(REVIEW).child(REVIEW_COUNT).get()


    override fun setReview(bookInfo: BookInfo, content: String): Task<DataSnapshot> {
        val reviewId = System.currentTimeMillis().toString()
        val bookDB = firebaseBookDB.child(bookInfo.isbn)
        val reviewDB = bookDB.child(REVIEW)
        val reviewIdDB = reviewDB.child(REVIEW_ID).child(reviewId)
        val reviewCountDB = reviewDB.child(REVIEW_COUNT)
        val review = HashMap<String,Any>()
        review[REVIEW_ID] = reviewId
        review[USER_ID] = uid.orEmpty()
        review[EMAIL] = getUserAuth()?.email.orEmpty()
        review[CONTENT] = content

        return firebaseBookDB.get().addOnSuccessListener {
            if(it.hasChild(bookInfo.isbn)){
                reviewIdDB.updateChildren(review).addOnSuccessListener {
                    reviewDB.child(REVIEW_ID).get().addOnSuccessListener { dataSnapShot ->
                        reviewCountDB.setValue(dataSnapShot.childrenCount)
                    }
                }
            } else{
                bookDB.setValue(bookInfo).addOnSuccessListener {
                    reviewIdDB.updateChildren(review).addOnSuccessListener {
                        reviewDB.child(REVIEW_ID).get().addOnSuccessListener { dataSnapShot ->
                            reviewCountDB.setValue(dataSnapShot.childrenCount)
                        }
                    }
                }
            }
        }
    }

    override fun deleteReview(isbn: String, reviewId: String): Task<Void> {
        val reviewDB = firebaseBookDB.child(isbn).child(REVIEW)
        return reviewDB.child(REVIEW_ID).child(reviewId).removeValue()
            .addOnSuccessListener {
                reviewDB.child(REVIEW_ID).get().addOnSuccessListener { dataSnapshot ->
                    reviewDB.child(REVIEW_COUNT).setValue(dataSnapshot.childrenCount)
                }
            }
    }



    override fun getRatingAverage(isbn: String): Task<DataSnapshot> =
        firebaseBookDB.child(isbn).child(RATING).child(RATING_AVERAGE).get()

    override fun setRating(ratingNum: Float, book: BookInfo) {
        val bookDB = firebaseBookDB.child(book.isbn)
        val ratingDB = firebaseBookDB.child(book.isbn).child(RATING)
        val ratedByDB = ratingDB.child(RATED_BY)
        val ratingAverageDB = ratingDB.child(RATING_AVERAGE)
        val ratedBy = HashMap<String, Any>()
        ratedBy[uid] = ratingNum

        firebaseBookDB.get().addOnSuccessListener {

            if(it.hasChild(book.isbn)){
                ratedByDB.updateChildren(ratedBy).addOnSuccessListener {
                    ratedByDB.get().addOnSuccessListener { dataSnapshot ->
                        var sum = 0.0
                        dataSnapshot.children.forEach {
                            sum += it.value.toString().toDouble()
                        }
                        ratingAverageDB.setValue(sum / dataSnapshot.childrenCount)
                    }
                }
            } else{
                bookDB.setValue(book).addOnSuccessListener {
                    ratedByDB.updateChildren(ratedBy).addOnSuccessListener {
                        ratedByDB.get().addOnSuccessListener { dataSnapshot ->
                            var sum = 0.0
                            dataSnapshot.children.forEach {
                                sum += it.value.toString().toDouble()
                            }
                            ratingAverageDB.setValue(sum / dataSnapshot.childrenCount)
                        }
                    }
                }
            }
        }
    }
}