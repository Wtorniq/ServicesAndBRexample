package com.example.servicesandbrexample.cp


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.servicesandbrexample.databinding.FragmentContentProviderBinding

class ContentProviderFragment: Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!

    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
        if (result){
            getContacts()
        } else {
            Toast.makeText(context, "need permission", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission(){
        context?.let{ notNullContext ->
            when (PackageManager.PERMISSION_GRANTED){
                ContextCompat.checkSelfPermission(notNullContext, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    @SuppressLint("Range")
    private fun getContacts() {
        context?.let { notNullContext ->
            // Отправляем запрос на получение контактов и получаем ответ в виде курсора
            val cursorWithContacts: Cursor? = notNullContext.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count){
                    // Переходим на позицию в курсоре
                    if (cursor.moveToPosition(i)) {
                        // Берем из курсора столбец с именем
                        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        addView(name)
                    }
                }
            }
            cursorWithContacts?.close()
        }

    }

    private fun addView(name: String) = with(binding) {
        containerForContacts.addView(TextView(requireContext()).apply {
            text = name
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentProviderFragment()
    }
}