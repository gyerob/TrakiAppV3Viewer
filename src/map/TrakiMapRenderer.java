package map;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.frustumM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setLookAtM;
import hu.gyerob.trakiapp.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import map.utils.ShaderHelper;
import map.utils.TextResourceReader;
import map.utils.TextureHelper;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

public class TrakiMapRenderer implements GLSurfaceView.Renderer {

	private static final int POSITION_COMPONENT_COUNT = 3;
	private static final int COLOR_COMPONENT_COUNT = 4;
	private static final int TEXTURE_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;

	private final FloatBuffer vertexData;
	private final FloatBuffer colorData;
	private final FloatBuffer textureData;

	private int mMVPMatrixHandle;
	private int mMVMatrixHandle;
	private int mPositionHandle;
	private int mColorHandle;
	private int mTextureUniformHandle;
	private int mTextureCoordHandle;
	
	private int mTextureDataHandle;

	private float[] mModelMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];

	private final Context context;

	private int program;

	private float[] vertices = {
			// órajárással ellentétesen.
			// X,			Y,			Z
			// 1.
			-0.309f,		1.0f,		0.951f,
			-0.309f,		-1.0f,		0.951f,
			0.309f,			1.0f,		0.951f,

			-0.309f,		-1.0f,		0.951f,
			0.309f,			-1.0f,		0.951f,
			0.309f,			1.0f,		0.951f,
			

			// 2.
			0.309f,			1.0f,		0.951f, 
			0.309f,			-1.0f,		0.951f, 
			0.809f,			1.0f,		0.588f,

			0.309f,			-1.0f,		0.951f,
			0.809f,			-1.0f,		0.588f,
			0.809f,			1.0f,		0.588f,

			// 3.
			0.809f,			1.0f,		0.588f, 
			0.809f,			-1.0f,		0.588f, 
			1.0f,			1.0f,		0.0f,

			0.809f,			-1.0f,		0.588f,
			1.0f,			-1.0f,		0.0f,
			1.0f,			1.0f,		0.0f,

			// 4.
			1.0f,			1.0f,		0.0f, 
			1.0f,			-1.0f,		0.0f, 
			0.809f,			1.0f,		-0.588f,

			1.0f,			-1.0f,		0.0f,			
			0.809f,			-1.0f,		-0.588f,			
			0.809f,			1.0f,		-0.588f,

			// 5.
			0.809f,			1.0f,		-0.588f, 
			0.809f,			-1.0f,		-0.588f, 
			0.309f,			1.0f,		-0.951f,

			0.809f,			-1.0f,		-0.588f,
			0.309f,			-1.0f,		-0.951f,
			0.309f,			1.0f,		-0.951f,

			// 6.
			0.309f,			1.0f,		-0.951f, 
			0.309f,			-1.0f,		-0.951f,
			-0.309f,		1.0f,		-0.951f,

			0.309f,			-1.0f,		-0.951f, 
			-0.309f,		-1.0f,		-0.951f,
			-0.309f,		1.0f,		-0.951f,

			// 7.
			-0.309f,		1.0f,		-0.951f, 
			-0.309f,		-1.0f,		-0.951f, 
			-0.809f,		1.0f,		-0.588f,

			-0.309f,		-1.0f,		-0.951f, 
			-0.809f,		-1.0f,		-0.588f, 
			-0.809f,		1.0f,		-0.588f,

			// 8.
			-0.809f,		1.0f,		-0.588f, 
			-0.809f,		-1.0f,		-0.588f, 
			-1.0f,			1.0f,		0.0f,

			-0.809f,		-1.0f,		-0.588f, 
			-1.0f,			-1.0f,		0.0f, 
			-1.0f,			1.0f,		0.0f,

			// 9.
			-1.0f,			1.0f,		0.0f, 
			-1.0f,			-1.0f,		0.0f,
			-0.809f,		1.0f,		0.588f,

			-1.0f,			-1.0f,		0.0f, 
			-0.809f,		-1.0f,		0.588f, 
			-0.809f,		1.0f,		0.588f,

			// 10.
			-0.809f,		1.0f,		0.588f, 
			-0.809f,		-1.0f,		0.588f, 
			-0.309f,		1.0f,		0.951f,

			-0.809f,		-1.0f,		0.588f,
			-0.309f,		-1.0f,		0.951f, 
			-0.309f,		1.0f,		0.951f,

			// fedél
			// X, 			Y, 			Z,
			// fent
			0.0f,			1.0f,		0.0f, 		// közép
			-0.309f,		1.0f,		0.951f,		// 1
			0.309f,			1.0f,		0.951f,		// 2
			0.809f,			1.0f,		0.588f,		// 3
			1.0f,			1.0f,		0.0f,		// 4
			0.809f,			1.0f,		-0.588f,	// 5
			0.309f,			1.0f,		-0.951f, 	// 6
			-0.309f,		1.0f,		-0.951f, 	// 7
			-0.809f,		1.0f,		-0.588f, 	// 8
			-1.0f,			1.0f,		0.0f, 		// 9
			-0.809f,		1.0f,		0.588f,		// 10
			-0.309f,		1.0f,		0.951f,		// lezárás

			// lent
			0.0f,			-1.0f,		0.0f, 		// közép
			0.309f,		-1.0f,		0.951f,		// 1
			-0.309f,			-1.0f,		0.951f,		// 2
			-0.809f,			-1.0f,		0.588f,		// 3
			-1.0f,			-1.0f,		0.0f,		// 4
			-0.809f,			-1.0f,		-0.588f,	// 5
			-0.309f,			-1.0f,		-0.951f, 	// 6
			0.309f,		-1.0f,		-0.951f, 	// 7
			0.809f,		-1.0f,		-0.588f, 	// 8
			1.0f,			-1.0f,		0.0f, 		// 9
			0.809f,		-1.0f,		0.588f,		// 10
			0.309f,		-1.0f,		0.951f,		// lezárás
	};

	private float[] colors = {
			// R 		G 			B 			A
			// 1.
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,

			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,

			// 2.
			1.0f,		0.5f,		0.0f,		1.0f,
			1.0f,		0.5f,		0.0f,		1.0f,
			1.0f,		0.5f,		0.0f,		1.0f,

			0.5f,		1.0f,		0.0f,		1.0f,
			0.5f,		1.0f,		0.0f,		1.0f,
			0.5f,		1.0f,		0.0f,		1.0f,

			// 3.
			1.0f, 		0.0f,		0.5f,		1.0f,
			1.0f,		0.0f,		0.5f,		1.0f,
			1.0f,		0.0f,		0.5f,		1.0f,

			0.0f,		1.0f,		0.5f,		1.0f,
			0.0f,		1.0f,		0.5f,		1.0f,
			0.0f,		1.0f,		0.5f,		1.0f,

			// 4.
			1.0f,		0.5f,		0.0f,		1.0f,
			1.0f,		0.5f,		0.0f,		1.0f,
			1.0f,		0.5f,		0.0f,		1.0f,

			0.5f,		1.0f,		0.5f,		1.0f,
			0.5f,		1.0f,		0.5f,		1.0f,
			0.5f,		1.0f,		0.5f,		1.0f,

			// 5.
			1.0f,		1.0f,		0.0f,		1.0f, 
			1.0f,		1.0f,		0.0f,		1.0f, 
			1.0f,		1.0f,		0.0f,		1.0f,

			0.0f,		1.0f,		1.0f,		1.0f,
			0.0f,		1.0f,		1.0f,		1.0f,
			0.0f,		1.0f,		1.0f,		1.0f,

			// 6.
			1.0f,		0.0f,		1.0f,		1.0f, 
			1.0f,		0.0f,		1.0f,		1.0f, 
			1.0f,		0.0f,		1.0f,		1.0f,

			1.0f,		1.0f,		1.0f,		1.0f,
			1.0f,		1.0f,		1.0f,		1.0f,
			1.0f,		1.0f,		1.0f,		1.0f,

			// 7.
			1.0f,		0.5f,		0.5f,		1.0f, 
			1.0f,		0.5f,		0.5f,		1.0f, 
			1.0f,		0.5f,		0.5f,		1.0f,

			0.5f,		0.5f,		0.5f,		1.0f,
			0.5f,		0.5f,		0.5f,		1.0f,
			0.5f,		0.5f,		0.5f,		1.0f,

			// 8.
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,

			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,

			// 9.
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,

			0.0f,		1.0f,		0.0f,		1.0f, 
			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,

			// 10.
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,

			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,
			0.0f,		1.0f,		0.0f,		1.0f,

			// fedél
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f,
	
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f, 
			1.0f,		0.0f,		0.0f,		1.0f,
			1.0f,		0.0f,		0.0f,		1.0f 
	};
	
	@SuppressWarnings("unused")
	private float[] normals = {
			//1.
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			//2.
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			//3.
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			
			//4.
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			
			//5.
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			
			//6.
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			
			//7.
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			
			//8.
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			
			//9.
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			
			//10.
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			//fedél
			//fent
			0.0f, 1.0f, 0.0f,	//kezdõ
			0.0f, 1.0f, 0.0f,	//1
			0.0f, 1.0f, 0.0f,	//2
			0.0f, 1.0f, 0.0f,	//3
			0.0f, 1.0f, 0.0f,	//4
			0.0f, 1.0f, 0.0f,	//5
			0.0f, 1.0f, 0.0f,	//6
			0.0f, 1.0f, 0.0f,	//7
			0.0f, 1.0f, 0.0f,	//8
			0.0f, 1.0f, 0.0f,	//9
			0.0f, 1.0f, 0.0f,	//10
			0.0f, 1.0f, 0.0f,	//zárás
			
			//lent
			0.0f, -1.0f, 0.0f,	//kezdõ
			0.0f, -1.0f, 0.0f,	//1
			0.0f, -1.0f, 0.0f,	//2
			0.0f, -1.0f, 0.0f,	//3
			0.0f, -1.0f, 0.0f,	//4
			0.0f, -1.0f, 0.0f,	//5
			0.0f, -1.0f, 0.0f,	//6
			0.0f, -1.0f, 0.0f,	//7
			0.0f, -1.0f, 0.0f,	//8
			0.0f, -1.0f, 0.0f,	//9
			0.0f, -1.0f, 0.0f,	//10
			0.0f, -1.0f, 0.0f,	//zárás
	};
	
	private float[] textures = {
			//1.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//2.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//3.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//4.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//5.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//6.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//7.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//8.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//9.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//10.
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
			
			//fedél
			//fent
			0.5f, 0.5f,
			-0.309f, 0.951f,
			0.309f, 0.951f,
			0.809f, 0.588f,
			1.0f, 0.0f,
			0.809f, 0.25f,
			0.309f, 0.0f,
			-0.309f, 0.0f,
			-0.809f, 0.25f,
			-1.0f, 0.0f,
			-0.809f, 0.588f,
			-0.309f, 0.951f,
			//lent
			0.5f, 0.5f,
			-0.309f, 0.951f,
			0.309f, 0.951f,
			0.809f, 0.588f,
			1.0f, 0.0f,
			0.809f, 0.25f,
			0.309f, 0.0f,
			-0.309f, 0.0f,
			-0.809f, 0.25f,
			-1.0f, 0.0f,
			-0.809f, 0.588f,
			-0.309f, 0.951f
	};

	public TrakiMapRenderer(Context context) {
		this.context = context;
		
		vertexData = ByteBuffer
				.allocateDirect(vertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexData.put(vertices).position(0);

		colorData = ByteBuffer.allocateDirect(colors.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		colorData.put(colors).position(0);
		
		textureData = ByteBuffer.allocateDirect(textures.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		textureData.put(textures).position(0);
	}

	@SuppressWarnings("unused")
	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		long time = SystemClock.uptimeMillis() % 10000L;        
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time); 
        
		glUseProgram(program);

		mMVPMatrixHandle = glGetUniformLocation(program, "u_MVPMatrix");
		mMVMatrixHandle = glGetUniformLocation(program, "u_MVMatrix");
		mTextureUniformHandle = glGetUniformLocation(program, "u_Texture");
		mPositionHandle = glGetAttribLocation(program, "a_Position");
		mColorHandle = glGetAttribLocation(program, "a_Color");
		mTextureCoordHandle = glGetAttribLocation(program, "a_TexCoordinate");
		
		// Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        
        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);
		
		//start fent
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 40.0f, 0.0f, -10.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		//start közép
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 40.0f, 0.0f, 0.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		//start lent
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 40.0f, 0.0f, 10.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 24.0f, 0.0f, 0.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 24.0f, 0.0f, -10.0f);
        Matrix.scaleM(mModelMatrix, 0, 1.5f, 1.5f, 1.5f);
		drawBale();
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 24.0f, 0.0f, 10.0f);
        Matrix.scaleM(mModelMatrix, 0, 1.5f, 1.5f, 1.5f);
		drawBale();
		
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 8.0f, 0.0f, -6.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 8.0f, 0.0f, -16.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 8.0f, 0.0f, 6.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 8.0f, 0.0f, 16.0f);
        Matrix.scaleM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f);
		drawBale();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);

		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 50.0f;

		frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.6f, 1.0f, 0.0f);

		// Use culling to remove back faces.
		glEnable(GLES20.GL_CULL_FACE);

		// Enable depth testing
		glEnable(GLES20.GL_DEPTH_TEST);

		// Position the eye in front of the origin.
		final float eyeX = 50.0f;
		final float eyeY = 5.0f;
		final float eyeZ = -4.0f;

		// We are looking toward the distance
		final float lookX = 0.0f;
		final float lookY = 5.0f;
		final float lookZ = -4.0f;

		// Set our up vector. This is where our head would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY,
				lookZ, upX, upY, upZ);

		String vertexShaderSource = TextResourceReader
				.readTextFileFromResource(context, R.raw.per_pixel_vertex_shader);
		String fragmentShaderSource = TextResourceReader
				.readTextFileFromResource(context, R.raw.per_pixel_fragment_shader);

		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper
				.compileFragmentShader(fragmentShaderSource);

		program = ShaderHelper.createandlinkProgram(vertexShader,
				fragmentShader, new String[] { "a_Position", "a_Color", "a_TexCoordinate" });
		
		mTextureDataHandle = TextureHelper.loadTexture(context, R.drawable.balaside);
	}

	public void drawBale() {
		// Pass in the position information
		vertexData.position(0);
		glVertexAttribPointer(mPositionHandle, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, 0, vertexData);

		glEnableVertexAttribArray(mPositionHandle);

		// Pass in the color information
		colorData.position(0);
		glVertexAttribPointer(mColorHandle, COLOR_COMPONENT_COUNT,
				GL_FLOAT, false, 0, colorData);

		glEnableVertexAttribArray(mColorHandle);
		
		// Pass in the texture coordinate information
        textureData.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, TEXTURE_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 
        		0, textureData);
        
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);

		// This multiplies the view matrix by the model matrix, and stores the
		// result in the MVP matrix
		// (which currently contains model * view).
		multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		// Pass in the modelview matrix.
		glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

		// This multiplies the modelview matrix by the projection matrix, and
		// stores the result in the MVP matrix
		// (which now contains model * view * projection).
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		// Pass in the combined matrix.
		glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

		// Draw the cube.
		glDrawArrays(GL_TRIANGLES, 0, 60);
		glDrawArrays(GL_TRIANGLE_FAN, 60, 12);
		glDrawArrays(GL_TRIANGLE_FAN, 72, 12);
	}

}
