package com.example.matule.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.matule.Home
import com.example.matule.Profile
import com.example.matule.ProfileMain
import com.example.matule.databinding.FragmentUserBinding
import com.example.matule.ui.user.UserViewModel

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userViewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.userProfile.setOnClickListener()
        {
            //this.context?.let {startActivity(Intent(it, Profile::class.java))}
            startActivity(Intent(context, ProfileMain::class.java))
        }

        /*val textView: TextView = binding.textUser
        userViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}