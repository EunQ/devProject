<template>
    <div>
        <detail :h_key="$route.params.id * 1"></detail>
    </div>
</template>
<script>
import detail from '../components/detail'
export default {
    name:'DetailPage',
    components:{
        detail
    },
    data(){
        return{

        }
    },
    
}
</script>