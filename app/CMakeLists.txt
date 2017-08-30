
cmake_minimum_required(VERSION 3.4.1)


SET(SoName ndkTest)

aux_source_directory(src/main/jni DIR_SRC)

add_library(
             ${SoName}

             SHARED
             ${DIR_SRC} )


find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


target_link_libraries( # Specifies the target library.
                        ${SoName}

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )