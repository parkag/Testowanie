from __future__ import print_function
from math import *

#filename = 'circle'
npts = 100

#f = open(filename, 'w')

print(npts)

for i in xrange(0,npts):
	#print ("%.3lf %.3lf" %(cos(2*pi/npts*i), sin(2*pi/npts*i)))
	print("%.3lf %.3lf" %(sin(2*pi/npts*10*i),sin(2*pi/npts*8*i )) )

