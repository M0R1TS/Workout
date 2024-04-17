package ru.devsokovix.workoutandroidapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class WorkoutActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var exerciseTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var completeButton: Button
    private lateinit var imageView: ImageView

    private var exerciseIndex = 0
    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer

    private val exercises = mutableListOf(
        Exercise(
            "Отжимания",
            "Поставьте руки на пол на ширине плеч. Опускайте тело до тех пор, пока грудь почти не коснется пола. Поднимите тело вверх, пока руки полностью не выпрямятся.",
            30,
            "https://media.tenor.com/gI-8qCUEko8AAAAC/pushup.gif"
        ),
        Exercise(
            "Приседания",
            "Встаньте, ноги на ширине плеч. Опустите корпус как можно ниже, отводя бедра назад и сгибая колени. Вернитесь в исходное положение.",
            45,
            "https://tenor.com/1nh9.gif"
        ),
        Exercise(
            "Планка",
            "Начните с позиции отжимания, затем согните руки в локтях и перенесите вес тела на предплечья. Удерживайте это положение как можно дольше",
            60,
            "https://media.tenor.com/6SOetkNbfakAAAAM/plank-abs.gif"
        )
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        titleTextView = findViewById(R.id.titleTextView)
        exerciseTextView = findViewById(R.id.exerciseTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        completeButton = findViewById(R.id.completeButton)
        imageView = findViewById(R.id.imageView)

        startButton.setOnClickListener{
            startWorkout()
        }
        completeButton.setOnClickListener {
            completeExercise()
        }
    }

    private fun startWorkout() {
        exerciseIndex = 0
        titleTextView.text = "Тренировка началась!"
        startButton.isEnabled = false
        startButton.text = "Тренировка в процессе..."
        startNextExercise()
    }

    private  fun completeExercise() {
        timer.cancel()
        completeButton.isEnabled = false
        startNextExercise()
    }

    private fun startNextExercise() {
        if (exerciseIndex < exercises.size) {
            currentExercise = exercises[exerciseIndex]
            exerciseTextView.text = currentExercise.name
            descriptionTextView.text = currentExercise.descriptor
            Glide.with(this@WorkoutActivity)
                .asGif()
                .load(currentExercise.gifImageUrl)
                .into(imageView)
            timerTextView.text = formatTime(currentExercise.durationInSeconds)

            timer = object : CountDownTimer(currentExercise.durationInSeconds * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTextView.text = formatTime((millisUntilFinished / 1000).toInt())

                }

                override fun onFinish() {
                    timerTextView.text = "Упражнение завершено"
                    imageView.visibility = View.VISIBLE
                    completeButton.isEnabled = true
                }
            }.start()

            exerciseIndex++
        } else {
            exerciseTextView.text = "Тренировка окончена"
            descriptionTextView.text = ""
            timerTextView.text = ""
            completeButton.isEnabled = false
            startButton.isEnabled = true
            startButton.text = "Начать заново"
        }
    }


    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

}