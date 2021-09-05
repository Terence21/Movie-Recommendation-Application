package temple.edu.random.activities.home.movies

import android.content.Context
import android.view.LayoutInflater
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import temple.edu.random.R
import temple.edu.random.databinding.CastMemberBinding

class CastMember(context: Context) : FlexboxLayout(context) {
    private val binding: CastMemberBinding

    init {
        LayoutInflater.from(context).inflate(R.layout.cast_member, this, true)
        binding = CastMemberBinding.bind(this)
    }

    private fun setFullNameText(full_name: String) {
        binding.castMemberFullNameTextView.text = full_name
    }

    private fun setCharacterNameText(character: String) {
        binding.castMemberCharacterNameTextView.text = character
    }

    private fun setCharacterProfilePhoto(imageUrl: String) {
        Picasso.with(context).load("$BASE_IMAGE_URL$imageUrl")
            .into(binding.castMemberHeadshotImageView)
    }

    fun handleCastMemberUpdate(castMember: CastMemberModel) = with(castMember) {
        setFullNameText(name)
        setCharacterNameText(character)
    }


    private companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"
    }
}