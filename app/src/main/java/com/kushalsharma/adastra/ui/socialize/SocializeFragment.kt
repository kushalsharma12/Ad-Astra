package com.kushalsharma.adastra.ui.socialize

import android.annotation.SuppressLint
import android.app.DownloadManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kushalsharma.adastra.R
import com.kushalsharma.adastra.daos.PostDao
import com.kushalsharma.adastra.modals.Post
import kotlinx.android.synthetic.main.fragment_socialize.*

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class SocializeFragment : Fragment(), IPostAdapter {

    private lateinit var auth: FirebaseAuth

    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_socialize, container, false)

        auth = Firebase.auth
        return root
    }



    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentUser = auth.currentUser!!

        firebaseName_socialize.text = "Hi " + currentUser.displayName!!.toString() + ","

        Glide.with(yourImg_socialize)
            .load(currentUser.photoUrl).circleCrop().into(yourImg_socialize)

        setupRecyclerView()


        yourImg_socialize.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_socialize_to_navigation_profile)
        }
        fab_addPost_socialize.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_socialize_to_addPostFragment)
        }
        floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_socialize_to_navigation_home)
        }
    }

    private fun setupRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt").limit(100)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        post_rv.adapter = adapter
        post_rv.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}