<template>
<!-- 버튼의 타입을 받아서 타입마다 다른 CSS를 적용할 수 있게  type으로 지정, 
 @click="$emit('click')"으로 클릭 이벤트를 부모 컴포넌트로부터 받아서 사용 -->
    <button 
        :type="type" 
        :class="mode" 
        @click="$emit('click')"> 
        <slot/>
    </button>
</template>

<sc