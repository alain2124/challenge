#!/usr/bin/env python

import time
from math import sqrt
import json
import random
def main():

    #run answer to question 1 
    toSort = [1,10,5,63,29,71,10,12,44,29,10,-1]
    #all sorting algorithms will return a separate copy to retain original toSort
    print "Array to sort: ", toSort
    print "Bubble Sorted: ", bubbleSort(toSort) #most inefficient O(n^2)
    print "\nArray to sort: ", toSort
    print "Merge Sorted: ", mergeSort(toSort) 

    newSort = list() #randomly generate array to sort
    for i in range(0, random.randint(1,15)):
        newSort.append(random.randint(-100,100))
    print "\nArray to sort: ", newSort
    print "Bubble Sorted: ", bubbleSort(newSort) #most inefficient O(n^2)
    print "\nArray to sort: ", newSort
    print "Merge Sorted: ", mergeSort(newSort) 

    print "\n"
    #run answer to question 2 loop through which nth prime we want
    for i in [3, 58, 10001] + [random.randint(5,10000) for _ in range(0,5)]:
        print "The %s%s prime number is %s" % (i,determineSuffix(i),findNthPrime(i))
    
    
    #run answer to question 3 
    jsonString = ''' 
        {"itemList": {"items": [
        {"id": "item1"},
        {"id": "item2","label": "Item 2"},
        {"id": "item3"},
        {"id": "item4"},
        {"id": "item5"},
        {"id": "subItem1",
        "subItems": [
        {"id": "subItem1Item1","label": "SubItem 1"},
        {"id": "subItem1Item2","label": "SubItem 2"},
        {"id": "subItem1Item3","label": "SubItem 3"}
        ]
        },
        {"id": "item6"},
        {"id": "item7","label": "Item 7"},
        {"id": "subItem2",
        "subItems": {"id": "item1","label": "SubItem 2 item1"}
        }
        ]}}
    '''
    jsonString2 = '''
{
    "firstName": "John",
    "lastName": "Smith",
    "age": 25,
    "address": {
        "streetAddress": "21 2nd Street",
        "city": "New York",
        "state": "NY",
        "postalCode": 10021
    },
    "phoneNumbers": [
        {
            "type": "home",
            "number": "212 555-1234"
        },
        {
            "type": "fax",
            "number": "646 555-4567" 
        }
    ] 
}
    '''
    print "\nJSON String: \n", jsonString 
    jsonObj = json.loads(jsonString)
    values = ["item1","item2", "SubItem 2", "SubItem 2 item1", "Item 7"]
    print "Depth First: "
    for value in values:
        print "\tPath to \"%s\"\n  " % value,findValuePath(value, jsonObj)

    print "\n\n"
    print"***" * 10
    print "JSON String2: \n", jsonString2 
    jsonObj2 = json.loads(jsonString2)
    values2 = ['NY', '646 555-4567', 10021, 'Smith']
    for value in values2:
        print "\nPath to \"%s\"\n  " % value,findValuePath(value, jsonObj2)

####QUESTION 1#####
# Sort the following list in numeric order
# 1,10,5,63,29,71,10,12,44,29,10,-1
##########################################

def bubbleSort(inList): 

    rtrList = inList[:] #make copy of inList to return, and preserve old copy 
    swapped = True
    numPasses = 0
    while numPasses < len(rtrList)-1 and swapped:
        swapped = False
        for i in xrange(len(rtrList)-1):
            if rtrList[i] > rtrList[i + 1]:
                temp = rtrList[i]
                rtrList[i] = rtrList[i+1]
                rtrList[i+1] = temp
                swapped = True
        numPasses += 1
    return rtrList
    
def mergeSort(inList):
    '''Cannot make copy since this is recursive'''
    if len(inList) <=1:
        return inList
    
    mid = len(inList)/2
    left = inList[:mid]
    right = inList[mid:]

    mergeSort(left)
    mergeSort(right)

    i = 0
    j = 0
    k = 0

    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            inList[k] = left[i]
            i+= 1
        else:
            inList[k] = right[j]
            j+=1
        k+=1

    while i < len(left):
        inList[k] = left[i]
        i+=1
        k+=1

    while j < len(right):
        inList[k] = right[j]
        j+=1 
        k+=1

    return inList

####QUESTION 2#####
#2. Find the 3rd, 58th and 10,001th prime number.
#################################################
def findNthPrime(n):

    if n < 1:
        print "Search must be at least 1"
        return None
    if n < 3:
        return n + 1

    primeIdx = 2
    num = 3
    while primeIdx != n:
        num += 2
        isPrime = True
        for i in xrange(3,int(sqrt(num))+1,2):
            if num % i == 0:
                isPrime = False
                break
        if isPrime:
            primeIdx += 1
    return num

def determineSuffix(n):

    j = n % 10
    i = n % 100
    if j == 1 and i != 11:
        return "st"
    if j == 2 and i != 12:
        return "nd"
    if j == 3 and i != 13:
        return "rd"
    return "th"

####QUESTION 3#####
# Get Full Json path of of any item for:
# ex. item2 -> \itemList\items\id[1]
#
#  {"itemList": 
#     {
#      "items": 
#          [
#              {"id": "item1"},
#              {"id": "item2","label": "Item 2"},
#              {"id": "item3"},
#              {"id": "item4"},
#              {"id": "item5"},
#              {"id": "subItem1",
#              "subItems": [
#              {"id": "subItem1Item1","label": "SubItem 1"},
#              {"id": "subItem1Item2","label": "SubItem 2"},
#              {"id": "subItem1Item3","label": "SubItem 3"}
#              ]
#              },
#              {"id": "item6"},
#              {"id": "item7","label": "Item 7"},
#              {"id": "subItem2",
#              "subItems": {"id": "item1","label": "SubItem 2 item1"}
#              }
#          ]
#     }
#   }
#################################################
def findValuePath(value, jsonObject):
    path = ''
    if isinstance(jsonObject, list):
        if value in jsonObject:
            return '[%s]' % jsonObject.index(value)
        #value not found dive deeper 
        for i,v in enumerate(jsonObject):
            pathAdd = findValuePath(value, v)
            if pathAdd:
                return '[%s]' % i + pathAdd
    elif isinstance(jsonObject, dict):
        for k,v in jsonObject.iteritems():
            if value == v:
                return "\\" + str(k)
        #value not found dive deeper 
        for k,v in jsonObject.iteritems():
            pathAdd = findValuePath(value, v)
            if pathAdd:
                return '\%s' % k + pathAdd
    return path

if __name__=="__main__":
    main()
