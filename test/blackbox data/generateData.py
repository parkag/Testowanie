from __future__ import print_function
from math import *

#filename = 'circle'
npts = 100000

#f = open(filename, 'w')

print(npts)

for i in xrange(0,npts):
	print ("%.5lf %.5lf" %(cos(2*pi/npts*i), sin(2*pi/npts*i)))
	#print("%.5lf %.5lf" %(sin(2*pi/npts*10*i),sin(2*pi/npts*8*i )) )

