package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ProfileFragment : Fragment() {

    // Profile data — update these as needed
    private val studentName    = "Tessa Esponilla"
    private val studentCourse  = "BS Information Technology"
    private val studentSection = "A"
    private val studentYear    = "3rd Year"
    private val hobbies        = listOf("Sleeping", "Relapse", "Reading")

    // Chip background colors (match the mockup: blue, green, amber cycling)
    private val chipColors = listOf(
        R.color.chip_blue,
        R.color.chip_green,
        R.color.chip_amber
    )
    private val chipTextColors = listOf(
        R.color.chip_text_blue,
        R.color.chip_text_green,
        R.color.chip_text_amber
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ── Profile photo ─────────────────────────────────────────────────
        // Replace R.drawable.ic_profile with your own image, e.g. R.drawable.my_photo
        view.findViewById<ImageView>(R.id.profileImage)
            .setImageResource(R.drawable.my_photo)

        // ── Hobby chips ───────────────────────────────────────────────────
        val chipGroup = view.findViewById<ChipGroup>(R.id.hobbiesChipGroup)
        hobbies.forEachIndexed { index, hobby ->
            val chip = Chip(requireContext()).apply {
                text          = hobby
                isClickable   = false
                isCheckable   = false
                isCloseIconVisible = false

                val bgColor   = chipColors[index % chipColors.size]
                val textColor = chipTextColors[index % chipTextColors.size]

                setChipBackgroundColorResource(bgColor)
                setTextColor(resources.getColor(textColor, null))
                textSize = 12f
            }
            chipGroup.addView(chip)
        }
    }
}