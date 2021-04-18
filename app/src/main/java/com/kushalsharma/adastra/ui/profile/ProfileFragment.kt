package com.kushalsharma.adastra.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kushalsharma.adastra.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = Firebase.auth

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentUser = auth.currentUser!!

        profileName.text = currentUser.displayName!!.toString()
        prfile_email.text = currentUser.email!!.toString()
        Glide.with(profile_Image)
            .load(currentUser.photoUrl).circleCrop().into(profile_Image)

        cancel_btton_toSocialize.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_profile_to_navigation_socialize)
        }

    }
}