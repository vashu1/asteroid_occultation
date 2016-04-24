#!/bin/python

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

NOISE_EVENTS_PER_SCOPE_SECOND = 10.0
FRAME_DURATION_SEC = 1 / 100.0

ASTEROID_R = 100
ASTEROID_MAX_SPEED = 10 * 1000
ASTEROID_MAX_SPEED = 10 * 1000
ASTEROID_MIN_SPEED = ASTEROID_MAX_SPEED / 10
ASTEROID_VX_VY_RATIO = 3

T_WIDTH  = 10
T_HEIGHT = 50
T_STEP_X = ASTEROID_R * 5
T_STEP_Y = ASTEROID_R

OCCULTATION_FLAG = 'occultation'
NOISE_FLAG = 'noise'

telescopes = {} # telescope_num: (x, y)
asteroid = () # (x, y, vx, vy)


def round_time_to_frame(t):
    return round(t / FRAME_DURATION_SEC) * FRAME_DURATION_SEC

def format(str):
    return str.replace('(', '').replace(')', '').replace(',', '').replace('\'', '') + os.linesep

def time_str(time):
    return "%.2f" % time

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

def list_with_noise(start, duration, actual_event, scope_num):
    duration_frames = duration / FRAME_DURATION_SEC
    noise_ratio = 1 / NOISE_EVENTS_PER_SCOPE_SECOND / FRAME_DURATION_SEC
    res = []
    for i in range(int(duration_frames)):
        if randint(0, noise_ratio) == 0:
            if not actual_event:
                res.append((scope_num, start + i * FRAME_DURATION_SEC, start + (i + 1) * FRAME_DURATION_SEC, NOISE_FLAG))
            else:
                if actual_event[2] <= i * FRAME_DURATION_SEC and actual_event[1] >= (i + 1) * FRAME_DURATION_SEC:
                    res.append((scope_num, start + i * FRAME_DURATION_SEC, start + (i + 1) * FRAME_DURATION_SEC, NOISE_FLAG))
        if actual_event:
            if abs(actual_event[1] - start - i * FRAME_DURATION_SEC) < 0.000001:
                res.append(actual_event)
    return res

def add_noise(events):
    max_event = max(events, key=lambda x: x[2]) # 2 is end time
    min_event = min(events, key=lambda x: x[1]) # 1 is start time
    event_to_scope = {}
    for event in events:
        # key is scope number
        event_to_scope[event[0]] = event
    pass_duration = round_time_to_frame(max_event[2] - min_event[1])
    pass_center = round_time_to_frame(min_event[1] + pass_duration / 2)
    res = []
    print len(events)
    for scope_num in range(T_WIDTH * T_HEIGHT):
        actual_event = None
        if event_to_scope.has_key(scope_num):
            actual_event = event_to_scope[scope_num]
        res.extend(list_with_noise(pass_center - 2 * pass_duration, 4 * pass_duration, actual_event, scope_num))
    return res

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
        t1  = round_time_to_frame(t1)
        t2  = round_time_to_frame(t2)
        if t1 == t2:
            t1 = t2 + FRAME_DURATION_SEC
        return (telescope_num, t2, t1, OCCULTATION_FLAG)
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

events = add_noise(events)

# output data
with open('asteroid.txt','w') as f:
    f.write(format(str(asteroid) + ' ' + str(ASTEROID_R)))
    f.close()

with open('events.txt','w') as f:
    for event in events:
        f.write(format(str(event)))
    f.close()

with open('telescope.txt','w') as f:
    for t_num in telescopes.keys():
        f.write(format(str(t_num) + ' ' + str(telescopes[t_num])))
    f.close()
