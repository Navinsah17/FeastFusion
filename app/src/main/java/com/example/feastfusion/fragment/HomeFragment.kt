package com.example.feastfusion.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.feastfusion.databinding.FragmentHomeBinding
import com.example.feastfusion.models.Meal
import com.example.feastfusion.models.MealList
import com.example.feastfusion.retrofit.RetrofitInstance
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var bindinghome : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindinghome = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return bindinghome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shimmer = Shimmer.AlphaHighlightBuilder() // You can customize the shimmer effect here
            .setDuration(1500) // Example duration
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        // Apply the ShimmerDrawable to the ImageView
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        bindinghome.meals.setImageDrawable(shimmerDrawable)

        RetrofitInstance.api.getRandomMeal().enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    //Log.d("TEST","meal id ${randomMeal.idMeal} name = ${randomMeal.strMeal}")
                    Glide.with(this@HomeFragment)
                        .load(randomMeal.strMealThumb)
                        .into(bindinghome.meals)
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("HomeFragment",t.message.toString())
            }
        })
    }
}