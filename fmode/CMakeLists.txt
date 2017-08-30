
cmake_minimum_required(VERSION 3.4.1)


set(SoName myFomde)

set(fmode_DIR ${CMAKE_SOURCE_DIR}/libs)

aux_source_directory(src/main/jni DIR_SRC)

add_library(
             ${SoName}
             SHARED
             ${DIR_SRC} )

add_library(fmode SHARED IMPORTED)
set_target_properties(fmode  PROPERTIES IMPORTED_LOCATION ${fmode_DIR}/${ANDROID_ABI}/libfmod.so)

add_library(fmodL SHARED IMPORTED)
set_target_properties(fmodL  PROPERTIES IMPORTED_LOCATION ${fmode_DIR}/${ANDROID_ABI}/libfmodL.so)


set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

#find_library( #变量名
#             fmode
#             #fmode  So库
#              libs/fmode  libs/fmodL)


target_link_libraries( # 库连接
                       ${SoName}
                        fmode
                        fmodL
                       ${log-lib})