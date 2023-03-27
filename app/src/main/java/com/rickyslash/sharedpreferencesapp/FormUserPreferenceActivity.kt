package com.rickyslash.sharedpreferencesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.rickyslash.sharedpreferencesapp.databinding.ActivityFormUserPreferenceBinding

class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field can't be empty"
        private const val FIELD_DIGIT_ONLY = "Only numerical allowed"
        private const val FIELD_IS_NOT_VALID = "Email not valid"
    }

    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityFormUserPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(this)

        userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        var actionBarTitle = ""
        var btnTitle = ""

        when (formType) {
            TYPE_ADD -> {
                actionBarTitle = "Add New Preferences"
                btnTitle = "Save"
            }
            TYPE_EDIT -> {
                actionBarTitle = "Edit Preferences"
                btnTitle = "Update"
                showPreferenceInForm()
            }
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.text = btnTitle

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_save) {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            val isLoveMetal = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

            if (name.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
            }

            if (name.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
                return
            }

            if (email.isEmpty()) {
                binding.edtEmail.error = FIELD_REQUIRED
                return
            }

            if (!isValidEmail(email)) {
                binding.edtEmail.error = FIELD_IS_NOT_VALID
                return
            }

            if (age.isEmpty()) {
                binding.edtAge.error = FIELD_REQUIRED
                return
            }

            if (phone.isEmpty()) {
                binding.edtPhone.error = FIELD_REQUIRED
                return
            }

            if (!TextUtils.isDigitsOnly(phone)) {
                binding.edtPhone.error = FIELD_DIGIT_ONLY
                return
            }

            saveUser(name, email, age, phone, isLoveMetal)

        }
    }

    private fun saveUser(name: String, email: String, age: String, phone: String, isLoveMetal: Boolean) {
        val userPreference = UserPreference(this)

        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phone
        userModel.isLove = isLoveMetal

        userPreference.setUser(userModel)
        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showPreferenceInForm() {
        binding.edtName.setText(userModel.name)
        binding.edtEmail.setText(userModel.age.toString())
        binding.edtAge.setText(userModel.age.toString())
        binding.edtPhone.setText(userModel.phoneNumber)
        if (userModel.isLove) binding.rbYes.isChecked = true else binding.rbNo.isChecked = true
    }

}