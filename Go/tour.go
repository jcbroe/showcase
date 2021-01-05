//This is just my tinkering with concepts I found interesting throughout the Go Tour they have to begin learning the language.
//It is not exhaustive, it is only complete up to the level I experimented with learning Go.

package main

import (
	"fmt"
	"math"
	"math/rand"
	"runtime"
	"time"
)

func add(x, y int) int {
	return x + y
}

func swap(x, y string) (string, string) {
	return y, x
}

func findroot(x float64) string {
	if x < 0 {
		return findroot(-x) + "i"
	}
	return fmt.Sprint(math.Sqrt(x))
}

func pow(x, n, lim float64) float64 {
	if v := math.Pow(x, n); v < lim {
		return v
	} else {
		fmt.Printf("%g >= %g\n", v, lim)
	}
	return lim
}

type Vertex struct {
	X int
	Y int
}

type Vortex struct {
	Lat, Long float64
}

func printSlice(s string, x []int) {
	fmt.Printf("%s len=%d cap=%d %v\n",
		s, len(x), cap(x), x)
}

var m = map[string]Vortex{
	"Bell Labs": Vortex{
		40.68433, -74.39967,
	},
	"Google": Vortex{
		37.42202, -122.08408,
	},
}

func main() {
	fmt.Println("Hello, 世界")
	fmt.Println("A number!", rand.Intn(10))
	fmt.Println("Pi is: ", math.Pi)
	fmt.Println(add(1, 2))
	fmt.Println(swap("Hello", "World"))
	a, b := swap("world", "hello")
	fmt.Println(a, b)

	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println(sum)

	sum2 := 1
	for sum2 < 1000 {
		sum2 += sum2
	}
	fmt.Println(sum2)

	fmt.Println(findroot(2), findroot(-4))

	fmt.Println(
		pow(3, 2, 10),
		pow(3, 3, 20),
	)

	fmt.Print("Go runs on ")
	//GOOS is an evaluation of which OS go is currently running on
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.\n", os)
	}

	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Good morning!")
	case t.Hour() < 17:
		fmt.Println("Good afternoon.")
	default:
		fmt.Println("Good evening.")
	}
	/*
		fmt.Println("counting")
		for i := 0; i < 10; i++ {
			//increments i 0 to 9
			//defer fmt.Println(i)
			//builds a stack
			//pops from the stack in LIFO order
		}
		fmt.Println("done")
	*/
	i, j := 42, 2701

	p := &i         // point to i
	fmt.Println(*p) // read i through the pointer
	*p = 21         // set i through the pointer
	fmt.Println(i)  // see the new value of i

	p = &j // point to j
	fmt.Println(*p)
	*p = *p / 37   // divide j through the pointer
	fmt.Println(j) // see the new value of j

	v := Vertex{1, 2}
	v.X = 4
	fmt.Println(v.X)

	var z [2]string
	z[0] = "hello"
	z[1] = "world"
	fmt.Println(z)

	primes := [6]int{2, 3, 5, 7, 11, 13}
	var s []int = primes[1:4]
	fmt.Println(s)
	s[0] = -1
	fmt.Println(s)
	fmt.Println(primes)

	q := []int{2, 3, 5, 7, 11, 13}
	fmt.Println(q)

	c := make([]int, 5)
	printSlice("c", c)

	var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
	for _, value := range pow {
		fmt.Printf("%d\n", value)
	}

	fmt.Println(m)

}
