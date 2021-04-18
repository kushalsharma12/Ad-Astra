package com.kushalsharma.adastra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kushalsharma.adastra.daos.PostDao
import kotlinx.android.synthetic.main.fragment_add_post.*


class AddPostFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var postDao: PostDao



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_add_post, container, false)
        auth = Firebase.auth
        postDao = PostDao()


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentUser = auth.currentUser!!

        userName_addPost.text =  currentUser.displayName!!.toString()
        email_addPost.text =  currentUser.email!!.toString()

        Glide.with(userImage_addPost)
            .load(currentUser.photoUrl).circleCrop().into(userImage_addPost)


        postBtn_addPost.setOnClickListener {

            val input = postText_addPost.text.toString().trim()
            if(input.isNotEmpty()) {
                postDao.addPost(input)
                Navigation.findNavController(it).navigate(R.id.action_addPostFragment_to_navigation_socialize)

            }
            else{
                Toast.makeText(this.context, "Please enter something in it!", Toast.LENGTH_SHORT).show()
            }

        }


    }
}