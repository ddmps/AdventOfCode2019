import java.io.File

interface AdventRunnable<T, O> {
    fun readFileAsLinesUsingBufferedReader(fileName: String): List<String> = File(fileName).bufferedReader().readLines()
    fun convertInput(): List<T>
    fun execute(input: List<T>): O
    fun main() {
        println(execute(convertInput()))
    }
}
