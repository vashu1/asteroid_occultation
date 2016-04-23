#!/bin/python

from random import randint

ASTEROID_R = 50
ASTEROID_MAX_SPEED = 10 * 1000
ASTEROID_VX_VY_RATIO = 3

T_WIDTH  = 10
T_HEIGHT = 10
T_STEP_X = ASTEROID_R
T_STEP_Y = ASTEROID_R

telescopes = {} # telescope_num: (x, y)
asteroid = () # (x, y, vx, vy)

def init_telescopes():
    for x in range(T_WIDTH):
        for y in range(T_HEIGHT):
		    telescopes[y + x * T_HEIGHT] = (x * T_STEP_X, y * T_STEP_Y)

def init_asteroid():
    xt = randint(1, T_WIDTH  * T_STEP_X - 1)
	yt = randint(1, T_HEIGHT * T_STEP_Y - 1)
	vx = randint(1, ASTEROID_MAX_SPEED)
	vy = randint(-vx / ASTEROID_VX_VY_RATIO, vx / ASTEROID_VX_VY_RATIO)
 
    t = randint(0,100)
	x = xt - vx * t
	y = yt - vy * t
	
	asteroid = (x, y, vx, vy)

def calc_event(telescope_num):
    xa = asteroid[0]
	ya = asteroid[1]
	vxa = asteroid[2]
	vya = asteroid[3]
    xt = telescopes[telescope_num][0]
    xt = telescopes[telescope_num][1]

	xd = xa - xt
    yd = ya - yt

	a = vxa * vxa + vya * vya
	b = 2 * (vx * xd + vy * yd)
	c = dx * dx + dy * dy - ASTEROID_R * ASTEROID_R

	D = b * b - 4 * a * c
	
	if D > 0:
	    t1 = (-b + sqrt(D)) / (2 * a)
		t2 = (-b - sqrt(D)) / (2 * a)
		print (telescope_num, t1, t2)
		#event_begin = (xa + vxa * t1, ya + vya * t1)
		#event_end = (xa + vxa * t2, ya + vya * t2)
		

def calc_events():
    for k in telescopes.keys():
	    calc_event(k)

init_telescopes()
print telescopes

init_asteroid()
print asteroid