# this is the car class where a car and its actions are defined
class Car:
    # constructor, nothing fancy
    def __init__(self, speed=0):
        self.speed = speed
        self.odometer = 0
        self.time = 0

    # state in this case is the motion of the car as defined by speed
    def say_state(self):
        print("I'm going {} kph!".format(self.speed))

    # simple functions for increasing/decreasing speed {
    def accelerate(self):
        self.speed += 5

    def brake(self):
        self.speed -= 5
    # }

    # this simulates distance and elapsed time for the purpose of the example
    def step(self):
        self.odometer += self.speed
        self.time += 1

    # uses the simulated distance and time to create an average
    def average_speed(self):
        if self.time != 0:
            return self.odometer / self.time
        else:
            pass


# this is the "driver"
if __name__ == '__main__':
    # initialize the car object
    my_car = Car()
    print("I'm a car")

    # get user input to perform actions
    while True:
        action = input("What should I do? [A]ccelerate, [B]rake, "
                       "show [O]dometer, or show average [S]peed?").upper()
        if action not in "ABOS" or len(action) != 1:
            print("I don't know how to do that")
            continue
        if action == 'A':
            my_car.accelerate()
        elif action == 'B':
            my_car.brake()
        elif action == 'O':
            print("The car has driven {} kilometers".format(my_car.odometer))
        elif action == 'S':
            print("The car's average speed was {} kph".format(my_car.average_speed()))
        # update "trip" and state
        my_car.step()
        my_car.say_state()
