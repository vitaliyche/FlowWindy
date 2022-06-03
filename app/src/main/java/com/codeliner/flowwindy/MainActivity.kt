package com.codeliner.flowwindy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.codeliner.flowwindy.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            binding.textView.text = "" //очищать поле результата после каждого нажатия
            val flowsCount = binding.editTextNumber.text.toString().toIntOrNull() ?: 0
            launchFlows(flowsCount)
        }
    }

    private fun launchFlows(flowsCount: Int) {

        (0 until flowsCount) //создать коллекцию значений
            .map { value ->
                flow {
                    delay((value + 1) * 100L)
                    emit(value + 1)
                } //преобразовать массив значений в массив flow
            }
            .merge() //мержить все flows в один
            .runningReduce { accumulator, value ->
                accumulator + value
            } //сложить новое значение с суммой предыдущих
            .onEach {
                val currentText = binding.textView.text.toString()
                binding.textView.text = "$currentText\n$it"
            } // вывести значения
            .launchIn(lifecycleScope) //избавиться от вложенности, запустить collect
    }
}

/*
Разработать Flow, который суммирует значения других Flow.
 1. UI - на экране должно быть 3 элемента.
 Поле ввода, кнопка запуска и текстовое поле для вывода информации.
 При нажатии на кнопку получаем N - число введенное в поле ввода
 и запускаем Flow.
 Результат работы нужно вывести в текстовое поле.
 Каждое обновление должно находиться на новой строчке.

2. Flow. Необходимо создать N Flow<Int>, каждый из которых после задержки в (index + 1) * 100, емитит значение index + 1.
 Т.е. Flow с индексом 0 с задержкой 100 эмитит значение 1, Flow с индексом 1 с задержкой 200 эмитит значение 2.

3. Суммирование. Результирующий Flow должен суммировать значения всех N Flow.
 Суммирующий Flow должен возвращать значение после обновления каждого из N Flow.

Пример.
 Введенное количество: 1
 Результат: 1

 Введенное количество: 2
 Результат: 1 3

 Введенное количество: 3
 Результат: 1 3 6

 Введенное количество: 10
 Результат: 1 3 6 10 15 21 28

Задание необходимо реализовать на языке Kotlin.
Результатом выполнения задания должен быть .apk файл и исходный код.
*/