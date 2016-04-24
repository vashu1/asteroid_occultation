#!/bin/python

#
#    Copyright (c) 2016 _andrewm_ at gmail.com, _elena.perchenko_ at gmail.com,
#    _ivan.e.bondar_ at gmail.com, _katia.a.bondar_ at gmail.com,
#    _mark.cepak_ at exemail.com.au, _wagner.esteban_ at gmail.com
#    (remove underscores from mail)
#
#    Permission is hereby granted, free of charge, to any person obtaining a copy
#    of this software and associated documentation files (the "Software"), to
#    deal in the Software without restriction, including without limitation the
#    rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
#    sell copies of the Software, and to permit persons to whom the Software is
#    furnished to do so, subject to the following conditions:
#
#    The above copyright notice and this permission notice shall be included in
#    all copies or substantial portions of the Software.
#
#    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
#    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
#    DEALINGS IN THE SOFTWARE.
#

# Asteroid's shadow is round and it's radius is always ASTEROID_R
# Shadow's speed is between ASTEROID_MIN_SPEED and ASTEROID_MAX_SPEED
# Vx is positive and Vy is smaller than Vx at least ASTEROID_VX_VY_RATIO times.
# that is because asteroids move on orbit in same direction as Earth and mostly in same plane - so it's speed in some range. 
# We cannot detect ones that are near 0, so we look for ones that are between nim and max, and later we can do -max, -min with same algorithm.
#
# We have a rectangle grid of telescope. Number of telescopes T_WIDTH * T_HEIGHT
# The distance between them is T_STEP_X and T_STEP_Y
#
# NO NOISE


import os
import math
from random import randint

ASTEROID_R = 100
ASTEROID_MAX_SPEED = 10 * 1000
ASTEROID_MAX_SPEED = 10 * 1000
ASTEROID_MIN_SPEED = ASTEROID_MAX_SPEED / 10
ASTEROID_VX_VY_RATIO = 3

T_WIDTH  = 10
T_HEIGHT = 10
T_STEP_X = ASTEROID_R / 2
T_STEP_Y = ASTEROID_R / 2

OCCULTATION_FLAG = 'occultation'
NOISE_FLAG = 'noise'

telescopes = {} # telescope_num: (x, y)
asteroid = () # (x, y, vx, vy)


def format(str):
    return str.replace('(', '').replace(')', '').replace(',', '').replace('\'', '') + os.linesep

def time_str(time):
    return "%.6f" % time

def init_telescopes():
    telescopes = {}
    for x in range(T_WIDTH):
        for y in range(T_HEIGHT):
            telescopes[y + x * T_HEIGHT] = (x * T_STEP_X, y * T_STEP_Y)
    return telescopes

def init_asteroid():
    xt = T_WIDTH  * T_STEP_X / 2
    yt = randint(0, T_HEIGHT * T_STEP_Y)
    vx = randint(0, ASTEROID_MAX_SPEED - ASTEROID_MIN_SPEED) + ASTEROID_MIN_SPEED
    vy = randint(-vx / ASTEROID_VX_VY_RATIO, vx / ASTEROID_VX_VY_RATIO)
 
    t = randint(100, 200)
    x = xt - vx * t
    y = yt - vy * t

    return (x, y, vx, vy)

def calc_event(telescope_num):
    xa = asteroid[0]
    ya = asteroid[1]
    vxa = asteroid[2]
    vya = asteroid[3]
    xt = telescopes[telescope_num][0]
    yt = telescopes[telescope_num][1]

    xd = xa - xt
    yd = ya - yt

    a = vxa * vxa + vya * vya
    b = 2 * (vxa * xd + vya * yd)
    c = xd * xd + yd * yd - ASTEROID_R * ASTEROID_R

    D = b * b - 4 * a * c

    if D > 0:
        t1 = (-b + math.sqrt(D)) / (2 * a)
        t2 = (-b - math.sqrt(D)) / (2 * a)
        return (telescope_num, time_str(t2), time_str(t1))
        #event_begin_point = (xa + vxa * t1, ya + vya * t1)
        #event_end_point = (xa + vxa * t2, ya + vya * t2)


def calc_events():
    events = []
    for k in telescopes.keys():
        event = calc_event(k)
        if event:
            events.append(event)
    return events

telescopes = init_telescopes()
#print telescopes

asteroid = init_asteroid()
#asteroid = (0,0,1,1)
#print asteroid

events = calc_events()

# output data
with open('asteroid.txt','w') as f:
    f.write(format(str(asteroid) + ' ' + str(ASTEROID_R)))
    f.close()

with open('events.txt','w') as f:
    for event in events:
        f.write(format(str(event) + ' ' + OCCULTATION_FLAG))
    f.close()

with open('telescope.txt','w') as f:
    for t_num in telescopes.keys():
        f.write(format(str(t_num) + ' ' + str(telescopes[t_num])))
    f.close()
